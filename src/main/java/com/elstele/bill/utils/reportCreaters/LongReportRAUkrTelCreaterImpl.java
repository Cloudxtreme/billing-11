package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class LongReportRAUkrTelCreaterImpl extends ReportCreater implements ReportCreaterInterface {

    @Autowired
    CallForCSVDataService callForCSVDataService;

    public void reportCreateMain(String path, String fileName){
        PrintStream bw = createFileForWriting(path, fileName);
        filePrintingCreate(bw);
    }

    public void filePrintingCreate(PrintStream bw) {
        Double costTotalForPeriod = 0.0;
        String provider = "1";
        List<String> listWithNumberA = getUniqueNumbersA();
        for (String numberA : listWithNumberA) {
            List<CallForCSV> callForCSVListByNumberA = getCallsFromDBByNumbersA(numberA);
            mainHeaderPrint(bw, numberA);
            Double costTotalForThisNumber = dataPrint(bw, callForCSVListByNumberA);
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
        List<String> listWithNumberA = callForCSVDataService.getUniqueNumberAWithProvider(startTime, endTime, "1");
        return listWithNumberA;
    }

    public List<CallForCSV> getCallsFromDBByNumbersA(String numberA) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<CallForCSV> result = callForCSVDataService.getCallForCSVByNumberAWithProvider(numberA, startTime, endTime, "1");
        return result;
    }

    public Double dataPrint(PrintStream bw, List<CallForCSV> callForCSVListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (CallForCSV callForCSVByNumberA : callForCSVListByNumberA) {
            String numberB = callForCSVByNumberA.getNumberB();
            String duration = callForCSVByNumberA.getDuration();
            String dirPrefix = callForCSVByNumberA.getDirPrefix();
            String descrOrg = callForCSVByNumberA.getDirDescrpOrg();
            Double costTotal = Double.parseDouble(callForCSVByNumberA.getCostCallTotal());
            Date startTimeVal = callForCSVByNumberA.getStartTime();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(startTimeVal);
            String shortNumberB = callForCSVByNumberA.getNumberB().substring(dirPrefix.length(), numberB.length());
                bw.printf("%-18s|%-4s|%-7s|%-11s|%-22s|%7.2f|\r\n",
                        reportDate,
                        duration,
                        dirPrefix,
                        shortNumberB,
                        descrOrg,
                        costTotal
                );
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }


}
