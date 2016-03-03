package com.elstele.bill.datasrv.impl;

import com.elstele.bill.csvFileParser.csvfactory.CSVFileFactory;
import com.elstele.bill.csvFileParser.csvFileParsers.csvinterface.CSVFileParser;
import com.elstele.bill.datasrv.interfaces.CSVFileDataService;
import com.elstele.bill.exceptions.IncorrectCSVFileTypeException;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpSession;
import java.util.Iterator;

@Service
public class CSVFileDataServiceImpl implements CSVFileDataService {
    @Autowired
    CSVFileFactory factory;
    @Autowired
    LocalDirPathProvider pathProvider;

    final static Logger LOGGER = LogManager.getLogger(CSVFileDataService.class);

    @Override
    public ResponseToAjax handle(MultipartHttpServletRequest fileFromServlet, String selectedFileType, HttpSession session) {
        try {
            if(UserStateStorage.checkForBusy()){
                return ResponseToAjax.BUSY;
            }else {
                LOGGER.info("File parsing started");
                Iterator<String> fileIterator = fileFromServlet.getFileNames();
                MultipartFile multipartFile = fileFromServlet.getFile(fileIterator.next());
                CSVFileParser csvFileParser = factory.getParser(determineFileNameByFlag(selectedFileType));
                String path = pathProvider.getCSVDirectoryPath();
                ResponseToAjax response = csvFileParser.parse(multipartFile, path);
                LOGGER.info("File parsed successfully");
                return response;
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseToAjax.INCORRECTTYPE;
        }
    }

    private String determineFileNameByFlag(String selectedFileType) throws IncorrectCSVFileTypeException {
        switch(selectedFileType.toLowerCase()){
            case "vega": return Constants.VEGA_CSV;
            case "ukrnet" : return Constants.UKR_NET_CSV;
            default: {
                LOGGER.info("incorrect csv file type from client");
                throw new IncorrectCSVFileTypeException("incorrect data from client. Those csv file type is not exists");
            }
        }
    }

}
