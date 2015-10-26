package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class LongReportCreaterImpl extends ReportCreater implements ReportCreaterInterface {

    @Autowired
    CallDataService callDataService;

    public void reportCreateMain(String path, String fileName) throws IOException {
        try {
            PrintStream bw = createFileForWriting(path, fileName);
            filePrintingCreate(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filePrintingCreate(PrintStream bw){
        Double costTotalForPeriod = 0.0;
        List<String> listWithNumberA = getUniqueNumbersA();
        for (String numberA : listWithNumberA) {
            List<Call> callsListByNumberA = getCallsFromDBByNumbersA(numberA);
            mainHeaderPrint(bw, numberA);
            Double costTotalForThisNumber = callDataPrint(bw, callsListByNumberA);
            costTotalForPeriod = endOfTheHeaderPrint(bw, costTotalForPeriod, costTotalForThisNumber);
        }
        String firstString = " Итого " + round(costTotalForPeriod, 2);
        bw.println(firstString);
        bw.close();
    }

    public List<String> getUniqueNumbersA() {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<String> listWithNumberA = new ArrayList<String>();
        listWithNumberA = callDataService.getUniqueNumberAFromCalls(startTime, endTime);
        return listWithNumberA;
    }

    public Double callDataPrint(PrintStream bw, List<Call> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;

        for (Call call : callListByNumberA) {
            String numberB = call.getNumberB();
            String duration = call.getDuration().toString();
            String dirPrefix = "0 while";
            String dirPrefixCutted = dirPrefix.substring(2, dirPrefix.length());
            String descrOrg = "0 while";
            Double costTotal = (double)call.getCostTotal();
            Date startTimeVal = call.getStartTime();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(startTimeVal);
            String shortNumberB = call.getNumberB().substring(dirPrefix.length(), numberB.length());
            bw.printf("%-18s|%-4s|%-7s|%-11s|%-22s|%7.2f|\r\n",
                    reportDate,
                    duration,
                    dirPrefixCutted,
                    shortNumberB,
                    descrOrg,
                    costTotal
            );

            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }

    public List<Call> getCallsFromDBByNumbersA(String numberA) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<Call> result = callDataService.getCallByNumberA(numberA, startTime, endTime);
        return result;
    }

}
