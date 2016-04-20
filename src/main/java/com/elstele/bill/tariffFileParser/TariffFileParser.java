package com.elstele.bill.tariffFileParser;

import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.domain.Direction;
import com.elstele.bill.domain.PreferenceRule;
import com.elstele.bill.domain.TariffZone;
import com.elstele.bill.exceptions.TariffFileIncorrectExtensionException;
import com.elstele.bill.filesWorkers.MultipartFileConverter;
import com.elstele.bill.tariffFileParser.dateParsers.DOCXDateGetter;
import com.elstele.bill.tariffFileParser.dateParsers.TariffFileDateGetter;
import com.elstele.bill.tariffFileParser.fileTemplates.TariffFileTemplateData;
import com.elstele.bill.tariffFileParser.linegetterFactory.TariffLineGetterFactory;
import com.elstele.bill.tariffFileParser.linegetters.TariffLineGetter;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.PatternSyntaxException;

@Service
public class TariffFileParser {

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

    private final static Logger LOGGER = LogManager.getLogger(TariffFileParser.class);

    public ResponseToAjax handleTariffFile(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session) {
        if (UserStateStorage.checkForBusy()) {
            return ResponseToAjax.BUSY;
        }
        TariffFileDateGetter tariffFileDateGetter = new TariffFileDateGetter(directionDataService);
        MultipartFileConverter fileConverter = new MultipartFileConverter(pathProvider);

        try {
            occupyHandlingProcess(session);

            File file = fileConverter.convertAndUploadTariffFile(multiPartHTTPServletRequestFiles);
            TariffLineGetter lineGetter = TariffLineGetterFactory.getLineGetter(file);
            List<String> linesFromFile = lineGetter.getDataLinesFromFile(file);

            Date validFrom = DOCXDateGetter.findDateInDOCXFile(file);
            Date validTo = tariffFileDateGetter.determineValidToDate(validFrom);

            correctPreviousRowsDate(validFrom);

            parseLinesFromFile(linesFromFile, session, validFrom, validTo);

            uploadedFileInfoDataService.setInfoAboutHandledTariffFile(file, session);

            return ResponseToAjax.SUCCESS;
        } catch (IOException | ParseException | PatternSyntaxException | PSQLException | ConstraintViolationException | TariffFileIncorrectExtensionException e) {
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

    private void correctPreviousRowsDate(Date validFrom) {
        LOGGER.info("Updated " + directionDataService.setValidToDateForDirections(validFrom) + " directions rows in the DB");
        LOGGER.info("Updated " + tariffZoneDataService.setValidToDateForZones(validFrom) + " Tariff Zones rows in the DB");
        LOGGER.info("Updated " + preferenceRuleDataService.setValidToDateForRules(validFrom) + " Preference Rules rows in the DB");
    }

    private void parseLinesFromFile(List<String> linesFromFile, HttpSession session, Date validFrom, Date validTo) throws PatternSyntaxException, PSQLException {
        int rowsCount = linesFromFile.size();
        int processedRows = 0;

        HashMap<Float, PreferenceRule> preferenceRuleHashMap = preferenceRuleDataService.getTariffMapFRomDBByDate(validFrom);
        HashMap<String, TariffZone> zoneMapFRomDBByDate = tariffZoneDataService.getZoneMapFromDBByDate(validFrom);
        HashMap<String, Direction> directionMapFromDB = directionDataService.getDirectionMapByValidFromDate(validFrom);

        for (String lineFromFile : linesFromFile) {
            String[] lineFromFileAsArray = lineFromFile.split(";");

            //Create TariffFile Template for objects fill
            List<String> networkPrefixesList = parseNetworkPrefixes(lineFromFileAsArray);
            TariffFileTemplateData transTemplate = new TariffFileTemplateData(lineFromFileAsArray, validFrom, validTo, networkPrefixesList);

            //Create Preference rule and put it to DB if it's not a duplicate, profileId Rule get back fot TariffZones
            int profileId = preferenceRuleDataService.handleRuleFromTariffFile(transTemplate, preferenceRuleHashMap);
            transTemplate.setProfileId(profileId);

            //Create TariffZones and put it to DB if it's not a duplicate, ZoneID we should give to Direction
            int zoneId = tariffZoneDataService.handleZonesFromTariffFile(transTemplate, zoneMapFRomDBByDate);
            transTemplate.setZoneId(zoneId);

            //Create Direction and put it to DB if it's not a duplicate
            directionDataService.handleDirectionFromTariffFile(directionMapFromDB, transTemplate);

            //count progress percentage for progress-bar
            processedRows++;
            float progress = (float) processedRows / rowsCount * 100;
            UserStateStorage.setProgressToObjectInMap(session, progress);
        }
    }

    private List<String> parseNetworkPrefixes(String[] lineFromFile) throws PatternSyntaxException {
        List<String> networkPrefixesList = new ArrayList<>();
        String[] prefArr = lineFromFile[2].split(",");
        for (String string : prefArr) {
            if (!string.contains("-")) {
                networkPrefixesList.add(string.replaceAll("[\\s\\u00A0]+$", "").trim());
            } else {
                String[] prefEndDiapasons = string.split("-");
                int startDiapason = Integer.parseInt(prefEndDiapasons[0].trim());
                int endDiapason = Integer.parseInt(prefEndDiapasons[1].trim());
                for (int i = startDiapason; i <= endDiapason; i++) {
                    networkPrefixesList.add(Integer.toString(i));
                }
            }
        }
        return networkPrefixesList;
    }

    private void freeHandlingProcess(HttpSession session) {
        UserStateStorage.setBusyToObjectInMap(session, false);
        LOGGER.info("Free " + session.getId() + " at the end of processing DOCX File");
    }
}
