package com.elstele.bill.reportCreaters.factory;

import com.elstele.bill.reportCreaters.creatersImpl.*;
import com.elstele.bill.utils.ResponseToAjax;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReportsFactory {
    final static Logger log = LogManager.getLogger(ReportsFactory.class);
    @Autowired
    LongReportRACreaterImpl longReportRACreater;
    @Autowired
    LongReportCreaterImpl longReportCreater;
    @Autowired
    LongReportVegaCreaterImpl longReportVegaCreater;
    @Autowired
    LongReportRAUkrTelCreaterImpl longReportRAUkrTelCreater;
    @Autowired
    LongReportRAVegaCreaterImpl longReportRAVegaCreater;
    @Autowired
    ShortReportCreaterImpl shortReportCreater;
    @Autowired
    ShortReportRECreaterImpl shortReportRECreater;
    @Autowired
    ShortReportREUkrTelCreaterImpl shortReportREUkrTelCreater;
    @Autowired
    ShortReportREVegaCreaterImpl shortReportREVegaCreater;
    @Autowired
    ShortReportVegaCreaterImpl shortReportVegaCreater;
    @Qualifier("localCallsDetailReportCreaterImpl")
    @Autowired
    LocalCallsDetailReportCreaterImpl localCallsDetailReportCreater;
    @Autowired
    LocalCallsMainReportCreaterImpl localCallsMainReportCreater;
    @Autowired
    LocalCallsCostReportCreaterImpl localCallsCostReportCreater;


    public ResponseToAjax getReporter(String[] json, String path) {
        try {
            log.info("File creating starts");
            String year = json[0];
            String month = json[1];
            for (String reportName : json) {
                switch (reportName) {
                    case "longReport":
                        longReportCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "longReportVega":
                        longReportVegaCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "longReportRA":
                        longReportRACreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "longReportRAUkrTel":
                        longReportRAUkrTelCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "longReportRAVega":
                        longReportRAVegaCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "shortReport":
                        shortReportCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "shortReportRE":
                        shortReportRECreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "shortReportREUkrTel":
                        shortReportREUkrTelCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "shortReportREVega":
                        shortReportREVegaCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "shortReportVega":
                        shortReportVegaCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "localCallsDetailReport":
                        localCallsDetailReportCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "localCallsMainReport":
                        localCallsMainReportCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    case "localCallsCostReport":
                        localCallsCostReportCreater.reportCreateMain(path, reportName, year, month);
                        break;
                    default:
                        log.error("File name " + reportName + " does not match with any report create method");
                }
            }
            log.info("Report's creating process is done");
        } catch (Exception e) {
            log.error(e);
            return ResponseToAjax.ERROR;
        }
        return ResponseToAjax.SUCCESS;
    }
}
