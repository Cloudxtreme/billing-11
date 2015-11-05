package com.elstele.bill.reportCreaters;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Date;
import java.util.List;

@Service
public class ShortReportREVegaCreaterImpl extends ReportCreater implements ReportCreaterInterface {
    @Autowired
    CallForCSVDataService callForCSVDataService;

    public void reportCreateMain(String path, String fileName, String year, String month) {
        PrintStream bw = createFileForWriting(path, fileName, year, month);
        filePrintingCreate(bw, year, month);
    }

    public void filePrintingCreate(PrintStream bw, String year, String month) {
        try {
            Double costTotalForPeriod = 0.0;
            List<String> listWithNumberA = getUniqueNumbersA(year, month);
            for (String numberA : listWithNumberA) {
                List<CallForCSV> callsListByNumberA = getCallsFromDBByNumbersA(numberA, year, month);
                Double costTotalForThisNumber = 0.0;
                costTotalForThisNumber = costTotalForThisNumberOperation(callsListByNumberA);
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

    public List<CallForCSV> getCallsFromDBByNumbersA(String numberA, String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<CallForCSV> result = callForCSVDataService.getCallForCSVByNumberAWithProvider(numberA, startTime, endTime, "2");
        return result;
    }

    public List<String> getUniqueNumbersA(String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<String> listWithNumberA = callForCSVDataService.getUniqueNumberAWithProvider(startTime, endTime, "2");
        return listWithNumberA;
    }

}
