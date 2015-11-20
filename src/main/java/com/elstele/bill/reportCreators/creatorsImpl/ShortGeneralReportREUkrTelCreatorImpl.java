package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import com.elstele.bill.reportCreators.reportsStringCreator.ReportStringCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;

public class ShortGeneralReportREUkrTelCreatorImpl implements ReportCreator {

    private CallForCSVDataService callForCSVDataService;
    final public static Logger log = LogManager.getLogger(ShortGeneralReportREUkrTelCreatorImpl.class);

    public ShortGeneralReportREUkrTelCreatorImpl(CallForCSVDataService callForCSVDataService) {
        this.callForCSVDataService = callForCSVDataService;
    }

    public void create(ReportDetails reportDetails) {
        Double costTotalForPeriod = 0.0;
        Date startTime = DateReportParser.parseStartTime(reportDetails);
        Date endTime = DateReportParser.parseEndTime(reportDetails);

        List<String> listWithNumberA = callForCSVDataService.getUniqueNumberAWithProvider(startTime, endTime, ReportConstants.UKRTEL_PROVIDER);
        PrintStream ps = FileCreator.createFileForWriting(reportDetails);
        for (String numberA : listWithNumberA) {
            List<CallForCSV> callsListByNumberA = callForCSVDataService.getCallForCSVByNumberAWithProvider(numberA, startTime, endTime, ReportConstants.UKRTEL_PROVIDER);
            ReportStringCreator stringCreator = new ReportStringCreator();
            List<String> stringList = stringCreator.createCSVStringsShort(numberA, callsListByNumberA);

            ReportStringsWriter.write(stringList, ps);
            CostTotalCounter costTotalCounter = new CostTotalCounter();
            costTotalForPeriod += costTotalCounter.countForCSV(callsListByNumberA);
        }
        String footerString = "Общая стоимость переговоров -  " + ReportStringCreator.round(costTotalForPeriod, 2) + " грн";
        if (ps != null) {
            ps.println(footerString);
            ps.close();
        }
        log.info("Report generating is Done");

    }
}
