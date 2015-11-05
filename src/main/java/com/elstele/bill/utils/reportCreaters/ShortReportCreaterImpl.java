package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.CallTransformerDir;
import com.elstele.bill.utils.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShortReportCreaterImpl extends  ReportCreater implements ReportCreaterInterface{
    @Autowired
    CallDataService callDataService;

    public void reportCreateMain(String path, String fileName){
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
            String firstString = numberA.substring(1,numberA.length()) + " " + round(costTotalForThisNumber, 2)+"\r\n";
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
        List<String> listWithNumberA = callDataService.getUniqueNumberAFromCalls(startTime, endTime);
        return listWithNumberA;
    }

    public List<CallTransformerDir> getCallsFromDBByNumbersA(String numberA) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<CallTransformerDir> result = callDataService.getCallByNumberA(numberA, startTime, endTime);
        return result;
    }



}
