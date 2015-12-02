package com.elstele.bill.csvFileParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class CSVFileFactory {
    final static Logger log = LogManager.getLogger(CSVFileFactory.class);

    public CSVFileParser getParser(String fileName) {
        CSVFileParser csvFileParser;
        switch (fileName) {
            case "csvUKRnet": {
                csvFileParser = null;
                break;
            }
            case "csv": {
                csvFileParser = null;
                break;
            }
            default: {
                csvFileParser = null;
                log.warn("File name does not match any cases");
            }
        }
        return csvFileParser;
    }
}
