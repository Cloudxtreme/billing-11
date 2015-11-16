package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.reportCreators.CostTotalCounter;
import com.elstele.bill.reportCreators.DBgetter.GetDataFromDB;
import com.elstele.bill.reportCreators.FileCreator;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.reportCreators.reportStringsWriter.ReportStringsWriter;
import com.elstele.bill.reportCreators.reportsStringCreator.ReportsStringCreator;
import com.elstele.bill.utils.CallTransformerDir;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.List;


public class LongGeneralReportCreatorImpl extends GeneralReportCreator implements ReportCreator {

    final public static Logger log = LogManager.getLogger(LongGeneralReportCreatorImpl.class);
    private CallDataService callDataService;
    private FileCreator creator = new FileCreator();

    public LongGeneralReportCreatorImpl(CallDataService callDataService) {
        this.callDataService = callDataService;
    }

    public void create(ReportDetails reportDetails) {
        PrintStream ps = creator.createFileForWriting(reportDetails);
        GetDataFromDB getDataFromDB = new GetDataFromDB(callDataService, reportDetails.getYear(), reportDetails.getMonth());
        try {
            Double costTotalForPeriod = 0.0;
            List<String> listWithNumberA = getDataFromDB.getUniqueNumbersA(reportDetails.getYear(), reportDetails.getMonth());
            for (String numberA : listWithNumberA) {
                List<CallTransformerDir> callsListByNumberA = getDataFromDB.getCallsFromDBByNumbersA(numberA, reportDetails.getYear(), reportDetails.getMonth());
                ReportsStringCreator stringCreator = new ReportsStringCreator(callsListByNumberA);
                List<String> stringList =  stringCreator.stringCreate(numberA);
                ReportStringsWriter writer = new ReportStringsWriter(ps);
                writer.write(stringList);
                CostTotalCounter costTotalCounter = new CostTotalCounter(callsListByNumberA);
                costTotalForPeriod += costTotalCounter.count();
            }
            String firstString = " Итого " + round(costTotalForPeriod, 2);
            ps.println(firstString);
            ps.close();
            log.info("Report generating is Done");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
