package com.elstele.bill.reportCreaters.creatersImpl;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.reportCreaters.reportParent.ReportCreater;
import com.elstele.bill.utils.CallTransformerDir;
import com.elstele.bill.reportCreaters.reportInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;

@Service
public class ShortReportVegaCreaterImpl extends ReportCreater implements ReportCreaterInterface {

    @Autowired
    CallDataService callDataService;

    public void reportCreateMain(String path, String fileName, String year, String month) {
        PrintStream bw = createFileForWriting(path, fileName, year, month);
        filePrintingCreate(bw, year, month);
    }

    public void filePrintingCreate(PrintStream bw, String year, String month) {
        try {
            Double costTotalForPeriod = 0.0;
            List<String> listWithNumberA = getUniqueNumbersA(year, month);
            for (String numberA : listWithNumberA) {
                List<CallTransformerDir> callsListByNumberA = getCallsFromDBByNumbersA(numberA, year, month);
                Double costTotalForThisNumber = 0.0;
                costTotalForThisNumber = costTotalForThisCallNumberOperation(bw, callsListByNumberA);
                costTotalForPeriod += costTotalForThisNumber;
                String firstString = numberA.substring(1, numberA.length()) + " " + round(costTotalForThisNumber, 2) + "\r\n";
                bw.println(firstString);
            }
            String firstString = "Общая стоимость переговоров -  " + round(costTotalForPeriod, 2) + " грн";
            bw.println(firstString);
            bw.close();
            log.info("Report generating is Done");
        } catch (Exception e) {
            log.error(e);
        }
    }


    public List<String> getUniqueNumbersA(String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<String> listWithNumberA = callDataService.getUniqueNumberAFromCallsWithTrunk(startTime, endTime, "05");
        return listWithNumberA;
    }

    public List<CallTransformerDir> getCallsFromDBByNumbersA(String numberA, String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<CallTransformerDir> result = callDataService.getCallByNumberAWithTrunk(numberA, startTime, endTime, "05");
        return result;
    }
}
