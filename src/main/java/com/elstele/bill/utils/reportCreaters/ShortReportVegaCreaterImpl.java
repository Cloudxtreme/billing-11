package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.CallTransformerDir;
import com.elstele.bill.utils.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.List;

@Service
public class ShortReportVegaCreaterImpl extends  ReportCreater implements ReportCreaterInterface {

    @Autowired
    CallDataService callDataService;

    public void reportCreateMain(String path, String fileName) {
        PrintStream bw = createFileForWriting(path, fileName);
        filePrintingCreate(bw);
    }

    public void filePrintingCreate(PrintStream bw){
        Double costTotalForPeriod = 0.0;
        List<String> listWithNumberA = getUniqueNumbersA();
        for (String numberA : listWithNumberA) {
            List<CallTransformerDir> callsListByNumberA = getCallsFromDBByNumbersA(numberA);
            Double costTotalForThisNumber = 0.0;
            costTotalForThisNumber = costTotalForThisCallNumberOperation(bw, callsListByNumberA);
            costTotalForPeriod += costTotalForThisNumber;
            String firstString = numberA.substring(1,numberA.length())+ " " + round(costTotalForThisNumber, 2)+"\r\n";
            bw.println(firstString);
        }
        String firstString = "Общая стоимость переговоров -  " + round(costTotalForPeriod, 2) + " грн";
        bw.println(firstString);
        bw.close();
    }


    public List<String> getUniqueNumbersA() {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<String> listWithNumberA = callDataService.getUniqueNumberAFromCallsWithTrunk(startTime, endTime, "05");
        return listWithNumberA;
    }

    public List<CallTransformerDir> getCallsFromDBByNumbersA(String numberA) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<CallTransformerDir> result = callDataService.getCallByNumberAWithTrunk(numberA, startTime, endTime, "05");
        return result;
    }
}
