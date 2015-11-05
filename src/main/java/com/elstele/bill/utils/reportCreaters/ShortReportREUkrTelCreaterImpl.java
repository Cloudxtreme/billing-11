package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

@Service
public class ShortReportREUkrTelCreaterImpl extends ReportCreater implements ReportCreaterInterface {
    @Autowired
    CallForCSVDataService callForCSVDataService;

    public void reportCreateMain(String path, String fileName) {
        PrintStream bw = createFileForWriting(path, fileName);
        filePrintingCreate(bw);
    }

    public void filePrintingCreate(PrintStream bw){
        Double costTotalForPeriod = 0.0;
        List<String> listWithNumberA = getUniqueNumbersA();
        for (String numberA : listWithNumberA) {
            List<CallForCSV> callsListByNumberA = getCallsFromDBByNumbersA(numberA);
            Double costTotalForThisNumber = 0.0;
            costTotalForThisNumber = costTotalForThisNumberOperation(callsListByNumberA);
            costTotalForPeriod += costTotalForThisNumber;
            String firstString = numberA.substring(1,numberA.length())+ " " + round(costTotalForThisNumber, 2)+"\r\n";
            bw.println(firstString);
        }
        String firstString = "Общая стоимость переговоров -  " + round(costTotalForPeriod, 2) + " грн";
        bw.println(firstString);
        bw.close();
    }

    public List<CallForCSV> getCallsFromDBByNumbersA(String numberA) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<CallForCSV> result = callForCSVDataService.getCallForCSVByNumberAWithProvider(numberA, startTime, endTime, "1");
        return result;
    }

    public List<String> getUniqueNumbersA() {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<String> listWithNumberA = callForCSVDataService.getUniqueNumberAWithProvider(startTime, endTime, "1");
        return listWithNumberA;
    }

}
