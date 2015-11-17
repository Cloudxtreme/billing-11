package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LongGeneralReportRAUkrTelCreatorImpl extends GeneralReportCreator implements ReportCreator {

    private CallForCSVDataService callForCSVDataService;

    public LongGeneralReportRAUkrTelCreatorImpl(CallForCSVDataService callForCSVDataService) {
        this.callForCSVDataService = callForCSVDataService;
    }

    public void create(ReportDetails reportDetails) {
        PrintStream bw = createFileForWriting(reportDetails);
        filePrintingCreate(bw, reportDetails.getYear(), reportDetails.getMonth());
    }

    public void filePrintingCreate(PrintStream bw, String year, String month) {
        try {
            Double costTotalForPeriod = 0.0;
            List<String> listWithNumberA = getUniqueNumbersA(year, month);
            for (String numberA : listWithNumberA) {
                List<CallForCSV> callForCSVListByNumberA = getCallsFromDBByNumbersA(numberA, year, month);
                mainHeaderPrint(bw, numberA);
                Double costTotalForThisNumber = dataPrint(bw, callForCSVListByNumberA);
                costTotalForPeriod = endOfTheHeaderPrint(bw, costTotalForPeriod, costTotalForThisNumber);
            }
            String firstString = " Итого " + round(costTotalForPeriod, 2);
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
        List<String> listWithNumberA = callForCSVDataService.getUniqueNumberAWithProvider(startTime, endTime, "1");
        return listWithNumberA;
    }

    public List<CallForCSV> getCallsFromDBByNumbersA(String numberA, String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
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
