package com.elstele.bill.csvFileParser.csvFileParsers.csvimpl;

import com.elstele.bill.filesWorkers.MultipartFileConverter;
import com.elstele.bill.csvFileParser.csvFileParsers.csvinterface.CSVFileParser;
import com.elstele.bill.csvFileParser.csvLineParsers.CSVFileCurrentLineParser;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CSVFileVEGAParser implements CSVFileParser {
    private CallForCSVDataService callForCSVDataService;
    final static Logger LOGGER = LogManager.getLogger(CSVFileVEGAParser.class);

    public CSVFileVEGAParser(CallForCSVDataService callForCSVDataService) {
        this.callForCSVDataService = callForCSVDataService;
    }

    @Override
    public ResponseToAjax parse(MultipartFile multipartFile, LocalDirPathProvider pathProvider) {
        CSVFileCurrentLineParser csvFileCurrentLineParser = new CSVFileCurrentLineParser();
        File file = MultipartFileConverter.convert(multipartFile, pathProvider);
        clearReportTable(file.getName());
        String line;
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            boolean firstLine = true;
            while ((line = fileReader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                } else {
                    CallForCSVForm callForCSVForm = csvFileCurrentLineParser.fillFormFromLine(line);
                    callForCSVDataService.addReportData(callForCSVForm);
                }
            }
            fileReader.close();
            return ResponseToAjax.SUCCESS;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseToAjax.ERROR;
        }
    }

    private void clearReportTable(String fileName) {
        if (!fileName.toLowerCase().contains("ukr")) {
            callForCSVDataService.clearReportTable();
            LOGGER.info("Table cleared");
        }
    }

}
