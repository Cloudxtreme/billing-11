package com.elstele.bill.datasrv.impl;

import com.elstele.bill.csvFileParser.csvfactory.CSVFileFactory;
import com.elstele.bill.csvFileParser.csvFileParsers.csvinterface.CSVFileParser;
import com.elstele.bill.datasrv.interfaces.CSVFileDataService;
import com.elstele.bill.exceptions.IncorrectCSVFileTypeException;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;

@Service
public class CSVFileDataServiceImpl implements CSVFileDataService {
    @Autowired
    CSVFileFactory factory;
    @Autowired
    LocalDirPathProvider pathProvider;
    final static Logger LOGGER = LogManager.getLogger(CSVFileDataService.class);

    @Override
    public ResponseToAjax handle(MultipartHttpServletRequest fileFromServlet, String selectedFileType) {
        try {
            LOGGER.info("File parsing started");
            Iterator<String> fileIterator = fileFromServlet.getFileNames();
            MultipartFile multipartFile = fileFromServlet.getFile(fileIterator.next());
            CSVFileParser csvFileParser = factory.getParser(determineFileNameByFlag(selectedFileType));
            ResponseToAjax response = csvFileParser.parse(multipartFile, pathProvider);
            LOGGER.info("File parsed successfully");
            return response;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return ResponseToAjax.ERROR;
        }
    }

    private String determineFileNameByFlag(String selectedFileType) throws IncorrectCSVFileTypeException {
        switch(selectedFileType.toLowerCase()){
            case "usual": return "current_csv";
            case "ukrnet" : return "current_ukr";
            default: {
                LOGGER.info("incorrect csv file type from client");
                throw new IncorrectCSVFileTypeException("incorrect data from client. Thoose csv file type is not exists");
            }
        }
    }

}
