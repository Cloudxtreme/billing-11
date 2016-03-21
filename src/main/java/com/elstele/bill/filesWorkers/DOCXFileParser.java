package com.elstele.bill.filesWorkers;

import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.PreferenceRule;
import com.elstele.bill.domain.TariffZone;
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

    final static Logger LOGGER = LogManager.getLogger(DOCXFileParser.class);
    private static final String DATE_PATTERN = "(0?[1-9]|[12][0-9]|3[01])[/|.](0?[1-9]|1[012])[/|.]((19|20)\\d\\d)";
    private static final String SIMPLE_DATE_FORMAT = "dd.mm.yyyy";

    public ResponseToAjax parse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session) {
        Iterator<String> fileIterator = multiPartHTTPServletRequestFiles.getFileNames();
        MultipartFile multipartFile = multiPartHTTPServletRequestFiles.getFile(fileIterator.next());
        String path = pathProvider.getDOCXDirectoryPath();
        File file = MultipartFileConverter.convert(multipartFile, path);
        LOGGER.info("DOCX File name to parse is : " + file.getName());

        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument(fis);
            Date validateFrom = findDateInDOCXFile(doc);
            LOGGER.info("Tariffs date validates from " + validateFrom);

            List<XWPFTable> tables = doc.getTables();

            for (XWPFTable table : tables) {
                List<XWPFTableRow> rowList = table.getRows();
                deletedFirstRow(rowList);
                int maxRuleProfId = preferenceRuleDataService.getProfileIdMaxValue();

                for (XWPFTableRow row : rowList) {
                    DOCXTransTemplate transTemplate = new DOCXTransTemplate(row, validateFrom);
                    PreferenceRule rule = new PreferenceRule();
                    rule.setTarif(Float.parseFloat(transTemplate.getTariff()));
                    rule.setProfileId(maxRuleProfId + 1);
                    rule.setValidFrom(validateFrom);
                    maxRuleProfId++;
                    if (preferenceRuleDataService.getByProfileIdAndPriority(rule.getProfileId(), rule.getRulePriority()) == null) {
                        preferenceRuleDataService.createRule(rule);
                    } else {
                        LOGGER.info("This rule " + rule.getTarif() + " with id " + rule.getProfileId() + " exists in DB");
                    }

                    TariffZone zone = new TariffZone();
                    zone.setDollar(true);
                    zone.setZoneName(transTemplate.getDirectionName());
                    zone.setPrefProfile(rule.getProfileId());
                    zone.setValidFrom(validateFrom);
                    int zoneId;
                    TariffZone zoneFromDB = tariffZoneDataService.getZoneByNameAndValidFrom(zone.getZoneName(), validateFrom);
                    if (zoneFromDB == null) {
                        zoneId = tariffZoneDataService.create(zone);
                    } else {
                        zoneId = zoneFromDB.getZoneId();
                        LOGGER.info("This zone " + zone.getZoneName() + " exists in DB");
                    }

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
            }

        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseToAjax.ERROR;
        }
        return ResponseToAjax.SUCCESS;
    }

    private void deletedFirstRow(List<XWPFTableRow> rowList) {
        if (rowList.get(0).getCell(0).getText().startsWith("Directions")) {
            rowList.remove(0);
            LOGGER.info("First row in table is uninformative, we deleted it");
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
