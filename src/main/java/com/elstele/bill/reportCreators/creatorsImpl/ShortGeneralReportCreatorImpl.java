package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import com.elstele.bill.reportCreators.reportsStringCreator.ReportStringCreator;
import com.elstele.bill.utils.CallTO;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.List;

public class ShortGeneralReportCreatorImpl implements ReportCreator {

    private CallDataService callDataService;
    final public static Logger log = LogManager.getLogger(ShortGeneralReportCreatorImpl.class);

    public ShortGeneralReportCreatorImpl(CallDataService callDataService) {
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
            ReportStringCreator stringCreator = new ReportStringCreator();
            List<String> stringList = stringCreator.createTOStringsShort(numberA, callsListByNumberA);
            ReportStringsWriter.write(stringList, ps);
            CostTotalCounter costTotalCounter = new CostTotalCounter();
            costTotalForPeriod += costTotalCounter.countForTO(callsListByNumberA);
        }
        String footerString = "Общая стоимость переговоров -  " + ReportStringCreator.round(costTotalForPeriod, 2) + " грн";
        if (ps != null) {
            ps.println(footerString);
            ps.close();
        }
        log.info("Report generating is Done");
    }

}
