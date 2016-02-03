package com.elstele.bill.csvFileParser.csvfactory;

import com.elstele.bill.csvFileParser.csvFileParsers.csvimpl.CSVFileCurrentParser;
import com.elstele.bill.csvFileParser.csvFileParsers.csvinterface.CSVFileParser;
import com.elstele.bill.csvFileParser.csvFileParsers.csvimpl.CSVFileUKRNETParser;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.exceptions.IncorrectCSVFileTypeException;
import com.elstele.bill.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CSVFileFactory {
    @Autowired
    CallForCSVDataService callForCSVDataService;
    final static Logger LOGGER = LogManager.getLogger(CSVFileFactory.class);

    public CSVFileParser getParser(String fileName) throws IncorrectCSVFileTypeException {
        CSVFileParser csvFileParser;
        switch (fileName) {
            case Constants.UKR_NET_CSV : {
                csvFileParser = new CSVFileUKRNETParser(callForCSVDataService);
                LOGGER.info("CSV File parser is URKNETParser");
                break;
            }
            case Constants.VEGA_CSV: {
                csvFileParser = new CSVFileCurrentParser(callForCSVDataService);
                LOGGER.info("CSV File parser is CurrentParser");
                break;
            }
            default: {
                LOGGER.warn("File name does not match any cases");
                throw new IncorrectCSVFileTypeException("File name does not match any cases");
            }
        }
        return csvFileParser;
    }
}
