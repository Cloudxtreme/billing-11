package com.elstele.bill.csvFileParser.csvFileParsers.csvimpl;

import com.elstele.bill.filesWorkers.MultipartFileConverter;
import com.elstele.bill.csvFileParser.csvFileParsers.csvinterface.CSVFileParser;
import com.elstele.bill.csvFileParser.csvLineParsers.CSVFileUKRNETLineParser;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class CSVFileUKRNETParser implements CSVFileParser {
    private CallForCSVDataService callForCSVDataService;
    final static Logger LOGGER = LogManager.getLogger(CSVFileUKRNETParser.class);

    public CSVFileUKRNETParser(CallForCSVDataService callForCSVDataService) {
        this.callForCSVDataService = callForCSVDataService;
    }

    @Override
    public ResponseToAjax parse(MultipartFile multipartFile, LocalDirPathProvider pathProvider) {
        String line;
        try {
            CSVFileUKRNETLineParser csvFileUKRNETLineParser = new CSVFileUKRNETLineParser(callForCSVDataService);
            File file = MultipartFileConverter.convert(multipartFile, pathProvider);
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            while ((line = fileReader.readLine()) != null) {
                CallForCSVForm callForCSVForm = csvFileUKRNETLineParser.arrayHandlingMethodCSVUkrNet(line);
                callForCSVDataService.addReportData(callForCSVForm);
            }
            fileReader.close();
            return ResponseToAjax.SUCCESS;
        } catch (IOException e){
            LOGGER.error(e);
            return ResponseToAjax.ERROR;
        }
    }

}
