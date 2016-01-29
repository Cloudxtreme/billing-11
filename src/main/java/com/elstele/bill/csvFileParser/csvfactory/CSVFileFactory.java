package com.elstele.bill.csvFileParser.csvfactory;

import com.elstele.bill.csvFileParser.csvFileParsers.csvimpl.CSVFileCurrentParser;
import com.elstele.bill.csvFileParser.csvFileParsers.csvinterface.CSVFileParser;
import com.elstele.bill.csvFileParser.csvFileParsers.csvimpl.CSVFileUKRNETParser;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.exceptions.IncorrectCSVFileTyleException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSVFileFactory {
    @Autowired
    CallForCSVDataService callForCSVDataService;
    final static Logger log = LogManager.getLogger(CSVFileFactory.class);

    public CSVFileParser getParser(String fileName) throws IncorrectCSVFileTyleException {
        CSVFileParser csvFileParser;
        switch (fileName) {
            case "current_ukr": {
                csvFileParser = new CSVFileUKRNETParser(callForCSVDataService);
                log.info("CSV File parser is URKNETParser");
                break;
            }
            case "current_csv": {
                csvFileParser = new CSVFileCurrentParser(callForCSVDataService);
                log.info("CSV File parser is CurrentParser");
                break;
            }
            default: {
                log.warn("File name does not match any cases");
                throw new IncorrectCSVFileTyleException("File name does not match any cases");
            }
        }
        return csvFileParser;
    }
}
