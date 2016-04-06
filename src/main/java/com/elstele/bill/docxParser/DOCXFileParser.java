package com.elstele.bill.docxParser;

import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.PreferenceRule;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.filesWorkers.MultipartFileConverter;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.FileStatus;
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
import java.util.*;
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
    @Autowired
    UploadedFileInfoDataService uploadedFileInfoDataService;

    private final static Logger LOGGER = LogManager.getLogger(DOCXFileParser.class);
    private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])[/|.](0?[1-9]|1[012])[/|.]((19|20)\\d\\d)";
    private DOCXTransTemplate transTemplate;
    private Date validateFrom;
    private Date validTo;
    private int maxRuleProfId;

    public ResponseToAjax parse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session) {
        if (UserStateStorage.checkForBusy()) {
            return ResponseToAjax.BUSY;
        }
        occupyHandlingProcess(session);
        File file = getFileForParse(multiPartHTTPServletRequestFiles);
        List<XWPFTable> tables = getTablesFromDOCXFile(file, session);
        if (tables.isEmpty()) {
            return ResponseToAjax.ERROR;
        }
        LOGGER.info("In this DOCX File we have : " + tables.size() + " tables");
        parseTable(tables, session);
        setProcessedFileInfoToDB(file, session);
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

    private List<XWPFTable> getTablesFromDOCXFile(File file, HttpSession session) {
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(fis);
            validateFrom = findDateInDOCXFile(docx);
            correctPreviousRowsDate();
            LOGGER.info("Tariffs date validates from " + validateFrom);
            return docx.getTables();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            UserStateStorage.setBusyToObjectInMap(session, false);
            return Collections.emptyList();
        }
    }

    private void correctPreviousRowsDate() {
        Direction withBiggerDate = directionDataService.getBiggerDate(validateFrom);
        if(withBiggerDate != null){
            validTo = DateReportParser.getPrevDayDate(withBiggerDate.getValidFrom());
        }else{
            validTo = null;
        }

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

        HashMap<Float, PreferenceRule> preferenceRuleHashMap = preferenceRuleDataService.getTariffMapFRomDBByDate(validateFrom);
        HashMap<String, TariffZone> zoneMapFRomDBByDate = tariffZoneDataService.getZoneMapFromDBByDate(validateFrom);
        HashMap<String, Direction> directionMapFromDB = directionDataService.getDirectionMapByValidFromDate(validateFrom);

        for (XWPFTableRow row : rowsList) {
            transTemplate = new DOCXTransTemplate(row, validateFrom);

            PreferenceRule rule = fillPrefRule();
            int profileId = getExistedProfileIdOrCreateNew(rule, preferenceRuleHashMap);

            TariffZone zone = fillTarZone(profileId);
            int zoneId = getExistedZoneIdOrCreateNew(zone, zoneMapFRomDBByDate);

            parseAndCreateDirection(zoneId, directionMapFromDB);
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
        rule.setValidTo(validTo);
        return rule;
    }

    private int getExistedProfileIdOrCreateNew(PreferenceRule rule, HashMap<Float, PreferenceRule> existedRulesHashMap) {
        PreferenceRule ruleFromDB = existedRulesHashMap.get(rule.getTarif());
        if (ruleFromDB == null) {
            rule.setProfileId(maxRuleProfId + 1);
            preferenceRuleDataService.createRule(rule);
            maxRuleProfId++;
            existedRulesHashMap.put(rule.getTarif(), rule);
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
        zone.setValidTo(validTo);
        return zone;
    }

    private int getExistedZoneIdOrCreateNew(TariffZone zone, HashMap<String, TariffZone> tariffZoneHashMap) {
        TariffZone zoneFromDB = tariffZoneHashMap.get(zone.getZoneName());
        if (zoneFromDB == null) {
            int zoneId = tariffZoneDataService.create(zone);
            tariffZoneHashMap.put(zone.getZoneName(), zone);
            return zoneId;
        } else {
            LOGGER.info("This zone " + zone.getZoneName() + " exists in DB");
            return zoneFromDB.getZoneId();
        }
    }

    private void parseAndCreateDirection(int zoneId, HashMap<String, Direction> directionHashMap) {
        for (String prefixEnd : transTemplate.getPrefEnder()) {
            String prefix = "00" + transTemplate.getPrefMainPart() + prefixEnd;
            if (directionHashMap.get(prefix) == null) {
                Direction direction = new Direction();
                direction.setValidFrom(validateFrom);
                direction.setValidTo(validTo);
                direction.setTarifZone(zoneId);
                direction.setPrefix(prefix);
                direction.setDescription(transTemplate.getDirectionName() + " " + prefix);
                directionDataService.createDirection(direction);
                directionHashMap.put(prefix, direction);
            } else {
                LOGGER.info("This direction " + prefix + " exists in DB");
            }
        }
    }

    private Date findDateInDOCXFile(XWPFDocument doc) {
        try {
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            SimpleDateFormat formatter = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
            Matcher m;
            for (XWPFParagraph paragraph : paragraphList) {
                m = Pattern.compile(DATE_PATTERN).matcher(paragraph.getText());
                if (m.find()) {
                    Date date = formatter.parse(m.group());
                    return DateReportParser.getOnlyDateFromLongValue(date.getTime());
                }
            }
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
        }
        LOGGER.info("Date matching did not find in paragraphs");
        return null;
    }

    private void setProcessedFileInfoToDB(File file, HttpSession session){
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        UploadedFileInfoForm fileInfo = new UploadedFileInfoForm();
        fileInfo.setFileSize(file.length());
        fileInfo.setFileName(file.getName());
        fileInfo.setPath(file.getName());
        fileInfo.setFileStatus(FileStatus.PROCESSED);
        fileInfo.setHandledBy(user.getUsername());
        uploadedFileInfoDataService.createOrUpdateFileInfo(fileInfo);
    }
}
