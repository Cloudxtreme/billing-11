package com.elstele.bill.reportCreators.factory;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.reportCreators.creatorsImpl.*;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.exceptions.IncorrectReportNameException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportCreatorFactory {
    @Autowired
    CallDataService callDataService;
    @Autowired
    CallForCSVDataService callForCSVDataService;
    final static Logger log = LogManager.getLogger(ReportCreatorFactory.class);

    public ReportCreator getCreator(String reportName) throws IncorrectReportNameException {
        ReportCreator reportCreator;
        switch (reportName) {
            case "longReport": {
                reportCreator = new LongGeneralReportCreatorImpl(callDataService);
                break;
            }
            case "longReportVega": {
                reportCreator = new LongGeneralReportVegaCreatorImpl(callDataService);
                break;
            }
            case "longReportRA": {
                reportCreator = new LongGeneralReportRACreatorImpl(callForCSVDataService);
                break;
            }
            case "longReportRAUkrTel": {
                reportCreator = new LongGeneralReportRAUkrTelCreatorImpl(callForCSVDataService);
                break;
            }
            case "longReportRAVega": {
                reportCreator = new LongGeneralReportRAVegaCreatorImpl(callForCSVDataService);
                break;
            }
            case "shortReport": {
                reportCreator = new ShortGeneralReportCreatorImpl(callDataService);
                break;
            }
            case "shortReportRE": {
                reportCreator = new ShortGeneralReportRECreatorImpl(callForCSVDataService);
                break;
            }
            case "shortReportREUkrTel": {
                reportCreator = new ShortGeneralReportREUkrTelCreatorImpl(callForCSVDataService);
                break;
            }
            case "shortReportREVega": {
                reportCreator = new ShortGeneralReportREVegaCreatorImpl(callForCSVDataService);
                break;
            }
            case "shortReportVega": {
                reportCreator = new ShortGeneralReportVegaCreatorImpl(callDataService);
                break;
            }
            case "localCallsDetailReport": {
                reportCreator = new LocalCallsDetailGeneralReportCreatorImpl(callDataService);
                break;
            }
            case "localCallsMainReport": {
                reportCreator = new LocalCallsMainGeneralReportCreatorImpl(callDataService);
                break;
            }
            case "localCallsCostReport": {
                reportCreator = new LocalCallsCostGeneralReportCreatorImpl(callDataService);
                break;
            }
            default: {
                log.debug(" report name does not match with any cases ");
                throw new IncorrectReportNameException("Report name does not match with any cases");
            }
        }
        return reportCreator;
    }
}
