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
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
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
import java.util.regex.PatternSyntaxException;

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

    public ResponseToAjax parse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session) {
        if (UserStateStorage.checkForBusy()) {
            return ResponseToAjax.BUSY;
        }
        try {
            occupyHandlingProcess(session);

            File file = getFileForParse(multiPartHTTPServletRequestFiles);

            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(fis);

            List<XWPFTable> tables = docx.getTables();
            if (tables.isEmpty()) {
                return ResponseToAjax.ERROR;
            }

            LOGGER.info("In this DOCX File we have : " + tables.size() + " tables");

            Date validFrom = findDateInDOCXFile(docx);
            Date validTo = determineValidToDate(validFrom);

            parseTable(tables, session, validFrom, validTo);
            setProcessedFileInfoToDB(file, session);

            fis.close();
            return ResponseToAjax.SUCCESS;
        }catch(IOException | ParseException | PatternSyntaxException | PSQLException | ConstraintViolationException e){
            LOGGER.error(e.getMessage(), e);
            return ResponseToAjax.ERROR;
        } finally {
            freeHandlingProcess(session);
        }
    }

    private void occupyHandlingProcess(HttpSession session) {
        UserStateStorage.setBusyToObjectInMap(session, true);
        LOGGER.info("Set busy for " + session.getId() + " while processing DOCX File");
    }

    private void freeHandlingProcess(HttpSession session) {
        UserStateStorage.setBusyToObjectInMap(session, false);
        LOGGER.info("Free " + session.getId() + " at the end of processing DOCX File");
    }

    private File getFileForParse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles) {
        Iterator<String> fileIterator = multiPartHTTPServletRequestFiles.getFileNames();
        MultipartFile multipartFile = multiPartHTTPServletRequestFiles.getFile(fileIterator.next());
        String path = pathProvider.getDOCXDirectoryPath();
        File file = MultipartFileConverter.convert(multipartFile, path);
        LOGGER.info("DOCX File name to parse is : " + file.getName());
        return file;
    }

    private Date determineValidToDate(Date validFrom) {
        correctPreviousRowsDate(validFrom);
        Direction withLatestDate = directionDataService.getDirectionWithLatestDate(validFrom);
        if (withLatestDate != null) {
            return DateReportParser.getPrevDayDate(withLatestDate.getValidFrom());
        } else {
            return null;
        }
    }

    private void correctPreviousRowsDate(Date validFrom) {
        LOGGER.info("Updated " + directionDataService.setValidToDateForDirections(validFrom) + " directions rows in the DB");
        LOGGER.info("Updated " + tariffZoneDataService.setValidToDateForZones(validFrom) + " Tariff Zones rows in the DB");
        LOGGER.info("Updated " + preferenceRuleDataService.setValidToDateForRules(validFrom) + " Preference Rules rows in the DB");
    }

    private void parseTable(List<XWPFTable> tables, HttpSession session, Date validFrom, Date validTo) throws PatternSyntaxException, PSQLException {
        for (XWPFTable table : tables) {
            List<XWPFTableRow> rowsList = table.getRows();
            deletedFirstRow(rowsList);
            parseRows(rowsList, session, validFrom, validTo);
        }
    }

    private void deletedFirstRow(List<XWPFTableRow> rowList) {
        if (rowList.get(0).getCell(0).getText().startsWith("Directions")) {
            rowList.remove(0);
            LOGGER.info("First row in table is uninformative, we deleted it");
        }
    }

    private void parseRows(List<XWPFTableRow> rowsList, HttpSession session, Date validFrom, Date validTo) throws PatternSyntaxException, PSQLException {
        int rowsCount = rowsList.size();
        int processedRows = 0;

        HashMap<Float, PreferenceRule> preferenceRuleHashMap = preferenceRuleDataService.getTariffMapFRomDBByDate(validFrom);
        HashMap<String, TariffZone> zoneMapFRomDBByDate = tariffZoneDataService.getZoneMapFromDBByDate(validFrom);
        HashMap<String, Direction> directionMapFromDB = directionDataService.getDirectionMapByValidFromDate(validFrom);

        for (XWPFTableRow row : rowsList) {
            //Create DOCX Template for objects fill
            List<String> networkPrefixesList = parseNetworkPrefixes(row);
            DOCXTemplateData transTemplate = new DOCXTemplateData(row, validFrom, validTo, networkPrefixesList);

            //Create Preference rule and put it to DB if it's not a duplicate, profileId Rule get back fot TariffZones
            int profileId = handleRuleObject(transTemplate, preferenceRuleHashMap);
            transTemplate.setProfileId(profileId);

            //Create TariffZones and put it to DB if it's not a duplicate, ZoneID we should give to Direction
            int zoneId = handleZonesObject(transTemplate, zoneMapFRomDBByDate);
            transTemplate.setZoneId(zoneId);

            //Create Direction and put it to DB if it's not a duplicate
            createDirection(directionMapFromDB, transTemplate);

            processedRows++;

            //count progress percentage for progress-bar
            float progress = (float) processedRows / rowsCount * 100;
            UserStateStorage.setProgressToObjectInMap(session, progress);
        }
    }

    private int handleRuleObject(DOCXTemplateData transTemplate, HashMap<Float, PreferenceRule> preferenceRuleHashMap) throws PSQLException {
        PreferenceRule rule = new PreferenceRule(transTemplate);
        return getExistedProfileIdOrCreateNew(rule, preferenceRuleHashMap);
    }

    private int getExistedProfileIdOrCreateNew(PreferenceRule rule, HashMap<Float, PreferenceRule> existedRulesHashMap) throws PSQLException {
        PreferenceRule ruleFromDB = existedRulesHashMap.get(rule.getTarif());
        if (ruleFromDB == null) {
            int profileId = preferenceRuleDataService.getProfileIdMaxValue();
            rule.setProfileId(profileId + 1);
            preferenceRuleDataService.createRule(rule);
            existedRulesHashMap.put(rule.getTarif(), rule);
            return rule.getProfileId();
        } else {
            LOGGER.info("This rule with Tariff = " + rule.getTarif() + " exists in DB");
            return ruleFromDB.getProfileId();
        }
    }

    private int handleZonesObject(DOCXTemplateData transTemplate, HashMap<String, TariffZone> zoneMapFRomDBByDate) throws PSQLException {
        TariffZone zone = new TariffZone(transTemplate);
        return getExistedZoneIdOrCreateNew(zone, zoneMapFRomDBByDate);
    }

    private int getExistedZoneIdOrCreateNew(TariffZone zone, HashMap<String, TariffZone> tariffZoneHashMap) throws PSQLException {
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

    private void createDirection(HashMap<String, Direction> directionHashMap, DOCXTemplateData transTemplate) throws PSQLException {
        for (String prefixEnd : transTemplate.getNetworkPrefixesList()) {
            String prefix = "00" + transTemplate.getCountryPrefix() + prefixEnd;
            if (directionHashMap.get(prefix) == null) {
                Direction direction = new Direction(transTemplate);
                direction.setPrefix(prefix);
                direction.setDescription(transTemplate.getDirectionName() + " " + prefix);
                directionDataService.createDirection(direction);
                directionHashMap.put(prefix, direction);
            } else {
                LOGGER.info("This direction " + prefix + " exists in DB");
            }
        }
    }

    private Date findDateInDOCXFile(XWPFDocument docx) throws ParseException {
        List<XWPFParagraph> paragraphList = docx.getParagraphs();
        SimpleDateFormat formatter = new SimpleDateFormat(Constants.SIMPLE_DATE_FORMAT);
        Matcher m;
        for (XWPFParagraph paragraph : paragraphList) {
            m = Pattern.compile(DATE_PATTERN).matcher(paragraph.getText());
            if (m.find()) {
                Date date = formatter.parse(m.group());
                return DateReportParser.getOnlyDateFromLongValue(date.getTime());
            }
        }
        LOGGER.info("Date matching did not find in paragraphs");
        return null;
    }

    private void setProcessedFileInfoToDB(File file, HttpSession session) {
        LocalUser user = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        UploadedFileInfoForm fileInfo = new UploadedFileInfoForm();
        fileInfo.setFileSize(file.length());
        fileInfo.setFileName(file.getName());
        fileInfo.setPath(file.getName());
        fileInfo.setFileStatus(FileStatus.PROCESSED);
        fileInfo.setHandledBy(user.getUsername());
        uploadedFileInfoDataService.createOrUpdateFileInfo(fileInfo);
    }

    private List<String> parseNetworkPrefixes(XWPFTableRow row) throws PatternSyntaxException{
        List<String>  networkPrefixesList = new ArrayList<>();
        String[] prefArr = row.getCell(2).getText().split(",");
        for (String string : prefArr) {
            if (!string.contains("-")) {
                networkPrefixesList.add(string.replaceAll("(^\\h*)|(\\h*$)", ""));
            } else {
                String[] prefEndDiapasons = string.split("-");
                int startDiapason = Integer.parseInt(prefEndDiapasons[0].replaceAll("\\s+", ""));
                int endDiapason = Integer.parseInt(prefEndDiapasons[1].replaceAll("\\s+", ""));
                for (int i = startDiapason; i <= endDiapason; i++) {
                    networkPrefixesList.add(Integer.toString(i));
                }
            }
        }
        return networkPrefixesList;
    }
}
