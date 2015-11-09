package com.elstele.bill.reportCreators.factory;

import com.elstele.bill.reportCreators.creatorsImpl.*;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ReportCreatorFactory {
    @Autowired
    LongGeneralReportCreatorImpl longGeneralReportCreator;
    @Autowired
    LongGeneralReportVegaCreatorImpl longGeneralReportVegaCreator;
    @Autowired
    LongGeneralReportRACreatorImpl longGeneralReportRACreator;
    @Autowired
    LongGeneralReportRAUkrTelCreatorImpl longGeneralReportRAUkrTelCreator;
    @Autowired
    LongGeneralReportRAVegaCreatorImpl longGeneralReportRAVegaCreator;
    @Autowired
    ShortGeneralReportCreatorImpl shortGeneralReportCreator;
    @Autowired
    ShortGeneralReportRECreatorImpl shortGeneralReportRECreator;
    @Autowired
    ShortGeneralReportREUkrTelCreatorImpl shortGeneralReportREUkrTelCreator;
    @Autowired
    ShortGeneralReportREVegaCreatorImpl shortGeneralReportREVegaCreator;
    @Autowired
    ShortGeneralReportVegaCreatorImpl shortGeneralReportVegaCreator;
    @Autowired
    @Qualifier("localCallsDetailGeneralReportCreatorImpl")
    LocalCallsDetailGeneralReportCreatorImpl localCallsDetailGeneralReportCreator;
    @Autowired
    LocalCallsMainGeneralReportCreatorImpl localCallsMainGeneralReportCreator;
    @Autowired
    LocalCallsCostGeneralReportCreatorImpl localCallsCostGeneralReportCreator;

    final static Logger log = LogManager.getLogger(ReportCreatorFactory.class);
    public ReportCreator getCreator(String reportName){
        ReportCreator reportCreator;
        switch (reportName) {
            case "longReport":{
                reportCreator = longGeneralReportCreator;
                break;
            }
            case "longReportVega":{
                reportCreator = longGeneralReportVegaCreator;
                break;
            }
            case "longReportRA":{
                reportCreator = longGeneralReportRACreator;
                break;
            }
            case "longReportRAUkrTel":{
                reportCreator =longGeneralReportRAUkrTelCreator;
                break;
            }
            case "longReportRAVega":{
                reportCreator = longGeneralReportRAVegaCreator;
                break;
            }
            case "shortReport":{
                reportCreator = shortGeneralReportCreator;
                break;
            }
            case "shortReportRE":{
                reportCreator = shortGeneralReportRECreator;
                break;
            }
            case "shortReportREUkrTel":{
                reportCreator = shortGeneralReportREUkrTelCreator;
                break;
            }
            case "shortReportREVega":{
                reportCreator = shortGeneralReportREVegaCreator;
                break;
            }
            case "shortReportVega":{
                reportCreator = shortGeneralReportVegaCreator;
                break;
            }
            case "localCallsDetailReport":{
                reportCreator = localCallsDetailGeneralReportCreator;
                break;
            }
            case "localCallsMainReport":{
                reportCreator = localCallsMainGeneralReportCreator;
                break;
            }
            case "localCallsCostReport":{
                reportCreator = localCallsCostGeneralReportCreator;
                break;
            }
            default:{
                reportCreator = null;
                log.warn(" report name does not match with any cases ");
            }
        }
        return reportCreator;
    }
}
