package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import com.elstele.bill.reportCreators.reportsStringCreator.ReportStringCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;

public class LocalCallsDetailGeneralReportCreatorImpl implements ReportCreator {


    private CallDataService callDataService;
    final public static Logger log = LogManager.getLogger(LocalCallsDetailGeneralReportCreatorImpl.class);

    public LocalCallsDetailGeneralReportCreatorImpl(CallDataService callDataService) {
        this.callDataService = callDataService;
    }

    public void create(ReportDetails reportDetails) {
        Float durationTotalForPeriod = 0.0f;
        Date startTime = DateReportParser.parseStartTime(reportDetails);
        Date endTime = DateReportParser.parseEndTime(reportDetails);

        List<String> listWithNumberA = callDataService.getUniqueLocalNumberAFromCalls(startTime, endTime);
        PrintStream ps = FileCreator.createFileForWriting(reportDetails);
        for (String numberA : listWithNumberA) {
            List<Call> callsListByNumberA = callDataService.getLocalCalls(numberA, startTime, endTime);
            ReportStringCreator stringCreator = new ReportStringCreator();
            List<String> stringList = stringCreator.createCallStrings(numberA, callsListByNumberA);
            ReportStringsWriter.write(stringList, ps);
            durationTotalForPeriod += CostTotalCounter.countForCall(callsListByNumberA);
        }
        String footerString = " Итого " + ReportStringCreator.round(durationTotalForPeriod, 2);
        if (ps != null) {
            ps.println(footerString);
            ps.close();
        }
        log.info("Report generating is Done");

    }


}
