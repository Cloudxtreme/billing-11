package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import com.elstele.bill.reportCreators.reportsStringCreator.ReportsStringCreator;
import com.elstele.bill.utils.CallTO;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.List;


public class LongGeneralReportCreatorImpl extends GeneralReportCreator implements ReportCreator {

    final public static Logger log = LogManager.getLogger(LongGeneralReportCreatorImpl.class);
    private CallDataService callDataService;

    public LongGeneralReportCreatorImpl(CallDataService callDataService) {
        this.callDataService = callDataService;
    }

    public void create(ReportDetails reportDetails) {
        Double costTotalForPeriod = 0.0;
        Date startTime = DateReportParser.parseStartTime(reportDetails);
        Date endTime = DateReportParser.parseEndTime(reportDetails);

        List<String> listWithNumberA = callDataService.getUniqueNumberAFromCalls(startTime, endTime);
        PrintStream ps = FileCreator.createFileForWriting(reportDetails);
        for (String numberA : listWithNumberA) {
            List<CallTO> callsListByNumberA = callDataService.getCallByNumberA(numberA, startTime, endTime);
            ReportsStringCreator stringCreator = new ReportsStringCreator();
            List<String> stringList = stringCreator.stringCreate(numberA, callsListByNumberA);
            ReportStringsWriter.write(stringList, ps);
            CostTotalCounter costTotalCounter = new CostTotalCounter();
            costTotalForPeriod += costTotalCounter.countForTO(callsListByNumberA);
        }
        String firstString = " Итого " + ReportsStringCreator.round(costTotalForPeriod, 2);
        ps.println(firstString);
        ps.close();
        log.info("Report generating is Done");
    }
}
