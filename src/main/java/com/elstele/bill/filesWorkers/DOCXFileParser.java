package com.elstele.bill.filesWorkers;

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
import java.util.Iterator;
import java.util.List;

@Service
public class DOCXFileParser {

    @Autowired
    LocalDirPathProvider pathProvider;
    final static Logger LOGGER = LogManager.getLogger(DOCXFileParser.class);

    public ResponseToAjax parse(MultipartHttpServletRequest multiPartHTTPServletRequestFiles, HttpSession session) {
        Iterator<String> fileIterator = multiPartHTTPServletRequestFiles.getFileNames();
        MultipartFile multipartFile = multiPartHTTPServletRequestFiles.getFile(fileIterator.next());
        String path = pathProvider.getDOCXDirectoryPath();
        File file = MultipartFileConverter.convert(multipartFile, path);
        LOGGER.info("DOCX File name to parse is : " + file.getName());
        try {
            FileInputStream fis = new FileInputStream(file);
            XWPFDocument doc = new XWPFDocument(fis);
            List<XWPFTable> tables=  doc.getTables();
            for(XWPFTable table : tables){
                List<XWPFTableRow> rowList = table.getRows();
                for(XWPFTableRow row : rowList){
                    List<XWPFTableCell> cellList =  row.getTableCells();
                    for(XWPFTableCell cell : cellList){
                        System.out.println(cell.getText());
                    }
                }
            }
        }catch(IOException e){
            LOGGER.error(e.getMessage() ,e);
        }finally {
            return ResponseToAjax.SUCCESS;
        }
    }
}
