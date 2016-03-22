package com.elstele.bill.docxParser;

import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.PreferenceRule;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.filesWorkers.MultipartFileConverter;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DOCXFileParser {

    @Autowired
    LocalDirPathProvider pathProvider;
    @Autowired
    DirectionDataService directionDataService;
    @Autowired
    TariffZoneDataService tariffZoneDataService;
    @Autowired
    PreferenceRuleDataService preferenceRuleDataService;

    private final static Logger LOGGER = LogManager.getLogger(DOCXFileParser.class);
    private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])[/|.](0?[1-9]|1[012])[/|.]((19|20)\\d\\d)";
    private static final String SIMPLE_DATE_FORMAT = "dd.mm.yyyy";
    private DOCXTransTemplate transTemplate;
    private Date validateFrom;
    private int maxRuleProfId;

    public ResponseToAjax parse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session) {
        if (UserStateStorage.checkForBusy()) {
            return ResponseToAjax.BUSY;
        }
        occupyHandlingProcess(session);
        File file = getFileForParse(multiPartHTTPServletRequestFiles);
        List<XWPFTable> tables = getTablesFromDOCXFile(file);
        if (tables.isEmpty()) {
            return ResponseToAjax.ERROR;
        }
        LOGGER.info("In this DOCX File we have : " + tables.size() + " tables");
        parseTable(tables, session);
        UserStateStorage.setBusyToObjectInMap(session, false);
        return ResponseToAjax.SUCCESS;
    }

    private void occupyHandlingProcess(HttpSession session) {
        UserStateStorage.setBusyToObjectInMap(session, true);
        LOGGER.info("Set busy for " + session.getId() + " while processing DOCX File");
    }

    private File getFileForParse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles) {
        Iterator<String> fileIterator = multiPartHTTPServletRequestFiles.getFileNames();
        MultipartFile multipartFile = multiPartHTTPServletRequestFiles.getFile(fileIterator.next());
        String path = pathProvider.getDOCXDirectoryPath();
        File file = MultipartFileConverter.convert(multipartFile, path);
        LOGGER.info("DOCX File name to parse is : " + file.getName());
        return file;
    }

    private List<XWPFTable> getTablesFromDOCXFile(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument(fis);
            validateFrom = findDateInDOCXFile(doc);
            correctPreviousValidToDate();
            LOGGER.info("Tariffs date validates from " + validateFrom);
            return doc.getTables();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private void correctPreviousValidToDate() {
        LOGGER.info("Updated " + directionDataService.setValidToDateForDirections(validateFrom) + " directions rows in the DB");
        LOGGER.info("Updated " + tariffZoneDataService.setValidToDateForZones(validateFrom) + " Tariff Zones rows in the DB");
        LOGGER.info("Updated " + preferenceRuleDataService.setValidToDateForRules(validateFrom) + " Preference Rules rows in the DB");
    }

    private void parseTable(List<XWPFTable> tables, HttpSession session) {
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rowsList = table.getRows();
            deletedFirstRow(rowsList);
            parseRows(rowsList, session);
        }
    }

    private void parseRows(List<XWPFTableRow> rowsList, HttpSession session) {
        int rowsCount = rowsList.size();
        int processedRows = 0;
        maxRuleProfId = preferenceRuleDataService.getProfileIdMaxValue();
        for (XWPFTableRow row : rowsList) {
            transTemplate = new DOCXTransTemplate(row, validateFrom);

            PreferenceRule rule = fillPrefRule();

            int profileId = getExistedProfileIdOrCreateNew(rule);

            TariffZone zone = fillTarZone(profileId);
            int zoneId = getExistedZoneIdOrCreateNew(zone);

            parseAndCreateDirection(zoneId);

            processedRows++;
            float progress = (float) processedRows / rowsCount * 100;
            UserStateStorage.setProgressToObjectInMap(session, progress);
        }
    }

    private void deletedFirstRow(List<XWPFTableRow> rowList) {
        if (rowList.get(0).getCell(0).getText().startsWith("Directions")) {
            rowList.remove(0);
            LOGGER.info("First row in table is uninformative, we deleted it");
        }
    }

    private PreferenceRule fillPrefRule() {
        PreferenceRule rule = new PreferenceRule();
        rule.setTarif(Float.parseFloat(transTemplate.getTariff()));
        rule.setValidFrom(validateFrom);
        return rule;
    }

    private int getExistedProfileIdOrCreateNew(PreferenceRule rule) {
        PreferenceRule ruleFromDB = preferenceRuleDataService.getByTariffAndValidDate(rule.getTarif(), validateFrom);
        if (ruleFromDB == null) {
            rule.setProfileId(maxRuleProfId + 1);
            preferenceRuleDataService.createRule(rule);
            maxRuleProfId++;
            return rule.getProfileId();
        } else {
            LOGGER.info("This rule with Tariff = " + rule.getTarif() +  " exists in DB");
            return ruleFromDB.getProfileId();
        }
    }

    private TariffZone fillTarZone(int profileId) {
        TariffZone zone = new TariffZone();
        zone.setPrefProfile(profileId);
        zone.setDollar(true);
        zone.setZoneName(transTemplate.getDirectionName());
        zone.setValidFrom(validateFrom);
        return zone;
    }

    private int getExistedZoneIdOrCreateNew(TariffZone zone) {
        TariffZone zoneFromDB = tariffZoneDataService.getZoneByNameAndValidFrom(zone.getZoneName(), validateFrom);
        if (zoneFromDB == null) {
            return tariffZoneDataService.create(zone);
        } else {
            LOGGER.info("This zone " + zone.getZoneName() + " exists in DB");
            return zoneFromDB.getZoneId();
        }
    }

    private void parseAndCreateDirection(int zoneId) {
        for (String prefixEnd : transTemplate.getPrefEnder()) {
            String prefix = "00" + transTemplate.getPrefMainPart() + prefixEnd;
            if (directionDataService.getDirectionByPrefixAndDate(prefix, validateFrom) == null) {
                Direction direction = new Direction();
                direction.setValidFrom(validateFrom);
                direction.setTarifZone(zoneId);
                direction.setPrefix(prefix);
                direction.setDescription(transTemplate.getDirectionName() + " " + prefix);
                directionDataService.createDirection(direction);
            } else {
                LOGGER.info("This direction " + prefix + " exists in DB");
            }
        }
    }

    private Date findDateInDOCXFile(XWPFDocument doc) {
        try {
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            SimpleDateFormat formatter = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
            Matcher m;
            for (XWPFParagraph paragraph : paragraphList) {
                m = Pattern.compile(DATE_PATTERN).matcher(paragraph.getText());
                if (m.find()) {
                    return formatter.parse(m.group());
                }
            }
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("Date matching did not find in paragraphs");
        return null;
    }


}
