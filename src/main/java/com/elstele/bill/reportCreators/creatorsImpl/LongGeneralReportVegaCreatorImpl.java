package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.utils.CallTransformerDir;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LongGeneralReportVegaCreatorImpl extends GeneralReportCreator implements ReportCreator {

    private CallDataService callDataService;

    public LongGeneralReportVegaCreatorImpl(CallDataService callDataService) {
        this.callDataService = callDataService;
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
                List<CallTransformerDir> callsListByNumberA = getCallsFromDBByNumbersA(numberA, year, month);
                mainHeaderPrint(bw, numberA);
                Double costTotalForThisNumber = 0.0;
                costTotalForThisNumber = callDataPrint(bw, callsListByNumberA);
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
        List<String> listWithNumberA;
        listWithNumberA = callDataService.getUniqueNumberAFromCallsWithTrunk(startTime, endTime, "05");
        return listWithNumberA;
    }

    public List<CallTransformerDir> getCallsFromDBByNumbersA(String numberA, String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<CallTransformerDir> result = callDataService.getCallByNumberAWithTrunk(numberA, startTime, endTime, "05");
        return result;
    }

    public Double callDataPrint(PrintStream bw, List<CallTransformerDir> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;

        for (CallTransformerDir call : callListByNumberA) {
            String numberB = call.getNumberb();
            String duration = call.getDuration().toString();
            String dirPrefix = call.getPrefix();
            String descrOrg = call.getDescription();
            Double costTotal = (double) call.getCosttotal();
            Date startTimeVal = call.getStarttime();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(startTimeVal);
            String shortNumberB = call.getNumberb().substring(dirPrefix.length(), numberB.length());
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
