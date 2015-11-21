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

public class LocalCallsCostGeneralReportCreatorImpl implements ReportCreator {

    final public static Logger log = LogManager.getLogger(LocalCallsCostGeneralReportCreatorImpl.class);
    private CallDataService callDataService;

    public LocalCallsCostGeneralReportCreatorImpl(CallDataService callDataService) {
        this.callDataService = callDataService;
    }

    public void create(ReportDetails reportDetails) {
        Float totalLocalCallsCost = 0.0f;
        Integer strIndex = 0;
        Date startTime = DateReportParser.parseStartTime(reportDetails);
        Date endTime = DateReportParser.parseEndTime(reportDetails);

        List<String> listWithNumberA = callDataService.getUniqueLocalNumberAFromCalls(startTime, endTime);
        PrintStream ps = FileCreator.createFileForWriting(reportDetails);
        for (String numberA : listWithNumberA) {
            strIndex++;
            List<Call> callsListByNumberA = callDataService.getLocalCalls(numberA, startTime, endTime);
            ReportStringCreator stringCreator = new ReportStringCreator();
            List<String> stringList = stringCreator.createCallsCostStringsShort(numberA, callsListByNumberA, strIndex);
            ReportStringsWriter.write(stringList, ps);
            totalLocalCallsCost += CostTotalCounter.countLocalForCall(callsListByNumberA);
        }
        String footerString = " Общая стоимость переговоров- " + ReportStringCreator.round(totalLocalCallsCost, 2) + " UAH";
        if (ps != null) {
            ps.println(footerString);
            ps.close();
        }
        log.info("Report generating is Done");
    }

}
