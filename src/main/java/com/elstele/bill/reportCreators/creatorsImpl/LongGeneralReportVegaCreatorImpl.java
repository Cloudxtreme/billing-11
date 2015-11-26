package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import com.elstele.bill.reportCreators.reportsStringCreator.ReportStringCreator;
import com.elstele.bill.reportCreators.CallTO;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.List;

public class LongGeneralReportVegaCreatorImpl implements ReportCreator {

    private CallDataService callDataService;
    final public static Logger log = LogManager.getLogger(LongGeneralReportVegaCreatorImpl.class);

    public LongGeneralReportVegaCreatorImpl(CallDataService callDataService) {
        this.callDataService = callDataService;
    }

    public void create(ReportDetails reportDetails) {
        Float costTotalForPeriod = 0.0f;
        Date startTime = DateReportParser.parseStartTime(reportDetails);
        Date endTime = DateReportParser.parseEndTime(reportDetails);

        List<String> listWithNumberA = callDataService.getUniqueNumberAFromCallsWithTrunk(startTime, endTime, ReportConstants.OUTPUT_TRUNK);
        PrintStream ps = FileCreator.createFileForWriting(reportDetails);
        for (String numberA : listWithNumberA) {
            List<CallTO> callsListByNumberA = callDataService.getCallByNumberAWithTrunk(numberA, startTime, endTime, ReportConstants.OUTPUT_TRUNK);
            ReportStringCreator stringCreator = new ReportStringCreator();
            List<String> stringList = stringCreator.createCallTOStrings(numberA, callsListByNumberA);
            ReportStringsWriter.write(stringList, ps);
            costTotalForPeriod += CostTotalCounter.countForTO(callsListByNumberA);
        }
        String footerString = " Итого " + ReportStringCreator.round(costTotalForPeriod, 2);
        if (ps != null) {
            ps.println(footerString);
            ps.close();
        }
        log.info("Report generating is Done");
    }
}
