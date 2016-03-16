package com.elstele.bill.filesWorkers;

import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.domain.Direction;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            List<XWPFTable> tables=  doc.getTables();
            for(XWPFTable table : tables){
                List<XWPFTableRow> rowList = table.getRows();
                rowList.remove(0);
                List<String> listForWrite = new ArrayList<>();
                String directionDesc = "";
                TariffZone tariffZoneOld = new TariffZone();
                for(XWPFTableRow row : rowList){
                    List<XWPFTableCell> cellList =  row.getTableCells();
                    String prefixPart = cellList.get(1).getText();
                    Direction direction = directionDataService.getByPrefixMainPart(prefixPart);

                    if(direction != null) {
                        LOGGER.info(direction.getDescription());
                        TariffZone tariffZoneNew = tariffZoneDataService.getUniqueZoneByZoneId(direction.getTarifZone());
                        if(!tariffZoneNew.equals(tariffZoneOld)){
                            tariffZoneOld = tariffZoneNew;
                        }

                    }else{
                        listForWrite.add("Tariff zone: " + cellList.get(0).getText() + " does not exist in DB.");
                        listForWrite.add("Prefix for this Zone is: "+ cellList.get(1).getText()+ " and Tariff: " + cellList.get(3).getText());
                        listForWrite.add("\n");
                    }
                }
                writeToFile(writer, listForWrite);
            }
            writer.close();
            return ResponseToAjax.SUCCESS;
        }catch(IOException e){
            LOGGER.error(e.getMessage() ,e);
            return ResponseToAjax.ERROR;
        }
    }

    private void writeToFile(PrintWriter writer, List<String> stringList){
        for(String s : stringList){
            writer.println(s);
        }
    }
}
