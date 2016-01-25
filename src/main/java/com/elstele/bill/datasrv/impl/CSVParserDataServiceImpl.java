package com.elstele.bill.datasrv.impl;

import com.elstele.bill.datasrv.interfaces.CSVParserDataService;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import com.elstele.bill.reportCreators.CallFromCSVFileToDBParser;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;

@Service
public class CSVParserDataServiceImpl implements CSVParserDataService {
    @Autowired
    CallFromCSVFileToDBParser callFromCSVFileToDBParser;
    @Autowired
    CallForCSVDataService callForCSVDataService;
    final static Logger LOGGER = LogManager.getLogger(CSVParserDataServiceImpl.class);

    @Override
    public ResponseToAjax parse(MultipartHttpServletRequest fileFromServlet) {

        if (fileFromServlet != null) {
            BufferedReader fileReader = null;
            Iterator<String> fileIterator = fileFromServlet.getFileNames();
            Locale.setDefault(new Locale("ru_RU.CP1251"));
            while (fileIterator.hasNext()) {
                try {
                    MultipartFile multipartFile = fileFromServlet.getFile(fileIterator.next());
                    File fileFromMulti = callFromCSVFileToDBParser.convert(multipartFile);
                    String fileName = fileFromMulti.getName();
                    if (!fileName.contains("ukr")) {
                        try {
                            callForCSVDataService.clearReportTable();
                            LOGGER.info("Table cleared");
                        } catch (QueryException e) {
                            LOGGER.warn(e);
                        }
                    }
                    String line;
                    fileReader = new BufferedReader(new FileReader(fileFromMulti));
                    boolean firstLine = true;
                    while ((line = fileReader.readLine()) != null) {
                        CallForCSVForm callForCSVForm;
                        if (!fileName.contains("ukr")) {
                            if (firstLine) {
                                firstLine = false;
                            } else {
                                callForCSVForm = callFromCSVFileToDBParser.arrayHandlingMethodCSV(line);
                                callForCSVDataService.addReportData(callForCSVForm);
                            }
                        } else {
                            callForCSVForm = callFromCSVFileToDBParser.arrayHandlingMethodCSVUkrNet(line);
                            callForCSVDataService.addReportData(callForCSVForm);
                        }
                    }

                } catch (Exception e) {
                    LOGGER.error(e);
                    return ResponseToAjax.ERROR;
                } finally {
                    //TODO next try-catch WTF?  what about try-with-resources
                    try {
                        assert fileReader != null;
                        fileReader.close();
                    } catch (IOException e) {
                        LOGGER.error(e);
                    }
                }
                return ResponseToAjax.SUCCESS;
            }

        }
        return ResponseToAjax.FULLOPERATION;
    }
}
