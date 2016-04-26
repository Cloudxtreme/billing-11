package com.elstele.bill.tariffFileParser.linegetters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DOCXLineGetter implements TariffLineGetter {
    private final static Logger LOGGER = LogManager.getLogger(DOCXLineGetter.class);


    public List<String> getDataLinesFromFile(File file){
        List<String> linesFromFile = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument docx = new XWPFDocument(fis);

            List<XWPFTable> tables = docx.getTables();

            for(XWPFTable table : tables){
                List<XWPFTableRow> rowList = table.getRows();
                for(int i = 1; i < rowList.size(); i ++){
                    XWPFTableRow row = rowList.get(i);
                    LOGGER.info("First row in table is uninformative, we skipped it");

                    StringBuilder lineFromFile = new StringBuilder()
                            .append(row.getCell(0).getText())
                            .append(";")
                            .append(row.getCell(1).getText())
                            .append(";")
                            .append(row.getCell(2).getText())
                            .append(";")
                            .append(row.getCell(3).getText())
                            .append(";");
                    linesFromFile.add(lineFromFile.toString());
                }
            }
            fis.close();
            return linesFromFile;
        }catch(IOException e){
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
