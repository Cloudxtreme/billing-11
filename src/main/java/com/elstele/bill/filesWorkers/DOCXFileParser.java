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
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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

    public ResponseToAjax parse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session) {
        Iterator<String> fileIterator = multiPartHTTPServletRequestFiles.getFileNames();
        MultipartFile multipartFile = multiPartHTTPServletRequestFiles.getFile(fileIterator.next());
        String path = pathProvider.getDOCXDirectoryPath();
        File file = MultipartFileConverter.convert(multipartFile, path);
        LOGGER.info("DOCX File name to parse is : " + file.getName());


        try {
            PrintWriter writer = new PrintWriter("D:/TarZonesCompares.txt", "UTF-8");
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument(fis);
            List<XWPFTable> tables = doc.getTables();
            for (XWPFTable table : tables) {
                List<XWPFTableRow> rowList = table.getRows();
                rowList.remove(0);
                List<String> listForWrite = new ArrayList<>();
                Map<TariffZone, List<DOCXTransTemplate>> repMap = new HashMap<>();
                for (XWPFTableRow row : rowList) {
                    List<XWPFTableCell> cellList = row.getTableCells();
                    String prefixPart = cellList.get(1).getText();
                    Direction direction = directionDataService.getByPrefixMainPart(prefixPart);

                    if (direction == null) {
                        listForWrite.add("Directions with Tariff zone: " + cellList.get(0).getText() + " in new and does not exist in DB.");
                        listForWrite.add("Prefix for this Zone is: " + cellList.get(1).getText() + " and Tariff: " + cellList.get(3).getText());
                        listForWrite.add("\n");
                    } else {
                        TariffZone tariffZone = tariffZoneDataService.getUniqueZoneByZoneId(direction.getTarifZone());
                        if (repMap.get(tariffZone) == null) {
                            repMap.put(tariffZone, new ArrayList<DOCXTransTemplate>());
                        } else {
                            DOCXTransTemplate template = new DOCXTransTemplate();
                            template.setDirectionName(cellList.get(0).getText());
                            template.setPrefMainPart(cellList.get(1).getText());
                            template.setPrefEnder(cellList.get(2).getText());
                            template.setTariff(cellList.get(3).getText());
                            repMap.get(tariffZone).add(template);
                        }
                    }
                }
                for (Map.Entry<TariffZone, List<DOCXTransTemplate>> entry : repMap.entrySet()) {
                    TariffZone tariffZone = entry.getKey();
                    List<DOCXTransTemplate> docxTransTemplateList = entry.getValue();
                    if (tariffZone != null) {
                        List<PreferenceRule> preferenceRules = preferenceRuleDataService.getRuleListByProfileId(tariffZone.getPrefProfile());
                        List<Float> priceArray = new ArrayList<>(preferenceRules.size());
                        for (PreferenceRule rule : preferenceRules) {
                            Float tariff = rule.getTarif();
                            if (tariff != null) {
                                priceArray.add(tariff);
                            }
                        }
                        listForWrite.add("\n");
                        listForWrite.add("\n");
                        if (priceArray.size() > 0) {
                            Collections.sort(priceArray);
                            String priceSpread = priceArray.get(0) + "-" + priceArray.get(priceArray.size() - 1);
                            LOGGER.info("Price spread is : " + priceSpread);
                            listForWrite.add("Tariff zone: " + tariffZone.getZoneName() + " with TARIFF = " + priceSpread + " is different from: ");
                        }else{
                            listForWrite.add("Tariff zone: " + tariffZone.getZoneName() + " which is FREE FOR CALL and is different from: ");
                        }
                    }
                    else{
                        listForWrite.add("Tariff zone is UNKNOWN or does not exist in DB but there is more than " + docxTransTemplateList.size() + " directions:" );
                    }
                    for (DOCXTransTemplate template : docxTransTemplateList) {
                        listForWrite.add("Name: " + template.getDirectionName() + " , AND prefix main part is: " +
                                template.getPrefMainPart() + " And prefix second part is : " + template.getPrefEnder() + " AND TARIFF = " + template.getTariff());
                    }
                }
                listForWrite.add("\n");
                listForWrite.add("________________________________________________________");
                listForWrite.add("Total counts of new directions are: " + rowList.size());

                writeToFile(writer, listForWrite);
            }
            writer.close();
            return ResponseToAjax.SUCCESS;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseToAjax.ERROR;
        }
    }

    private void writeToFile(PrintWriter writer, List<String> stringList) {
        for (String s : stringList) {
            writer.println(s);
        }
    }
}
