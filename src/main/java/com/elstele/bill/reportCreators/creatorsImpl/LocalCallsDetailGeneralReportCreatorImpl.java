package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LocalCallsDetailGeneralReportCreatorImpl extends GeneralReportCreator implements ReportCreator {


    private CallDataService callDataService;

    public LocalCallsDetailGeneralReportCreatorImpl(CallDataService callDataService) {
        this.callDataService = callDataService;
    }

    public void create(ReportDetails reportDetails) {
        PrintStream bw = createFileForWriting(reportDetails);
        filePrintingCreate(bw, reportDetails.getYear(), reportDetails.getMonth());
    }

    public void filePrintingCreate(PrintStream bw, String year, String month) {
        try {
            Double totalCallDuration = 0.0;
            List<String> listWithNumberA = getUniqueNumbersA(year, month);
            for (String numberA : listWithNumberA) {
                List<Call> callsListByNumberA = getCallsFromDBByNumbersA(numberA, year, month);
                mainHeaderPrint(bw, numberA);
                Double callDurationTotalForThisNumber = callDataPrint(bw, callsListByNumberA);
                totalCallDuration = endOfTheHeaderPrint(bw, totalCallDuration, callDurationTotalForThisNumber);
            }
            String firstString = " Итого " + round(totalCallDuration, 2);
            bw.println(firstString);
            bw.close();
            log.info("Report generating is Done");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public void mainHeaderPrint(PrintStream bw, String numberA) {

        String numberAShort = "";
        if (numberA.length() == 10) {
            String ourNumberA = numberA.substring(3, 8);
            numberAShort = ourNumberA.substring(1, 7);
        } else {
            numberAShort = numberA.substring(1, numberA.length());
        }
        String firstString = "";

        firstString = "Номер телефона, с которого звонили: " + numberAShort;
        bw.println(firstString);

        firstString = "--------------------------------------------------------------------------------";
        bw.println(firstString);

        firstString = "          |             |             Кому звонили                 |       |    ";
        bw.println(firstString);

        firstString = "   Дата   |  Время |Длит|------------------------------------------| Сумма |Зак.";
        bw.println(firstString);

        firstString = "          |             |  Код  | Телефон   | Город или страна     |       |    ";
        bw.println(firstString);

        firstString = "----------|--------|----|-------|-----------|----------------------|-------|----";
        bw.println(firstString);
    }

    public Double callDataPrint(PrintStream bw, List<Call> callListByNumberA) {
        Double callDurationTotalForThisNumber = 0.0;

        for (Call call : callListByNumberA) {
            String numberB = call.getNumberB();
            Long duration = call.getDuration();
            String dirPrefix = "";
            String descrOrg = "";
            Double costTotal = 0.0;
            Date startTimeVal = call.getStartTime();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(startTimeVal);
            String shortNumberB = numberB;
            if (numberB.startsWith("97") && numberB.length() == 8) {
                shortNumberB = numberB.substring(1, 8);
            }
            if (numberB.startsWith("92") || numberB.startsWith("93") || numberB.startsWith("94") || numberB.startsWith("95") || numberB.startsWith("96") && numberB.length() == 7) {
                shortNumberB = numberB.substring(1, 7);
            }

            bw.printf("%-18s|%-4s|%-7s|%-11s|%-22s|%7.2f|\r\n",
                    reportDate,
                    duration,
                    dirPrefix,
                    shortNumberB,
                    descrOrg,
                    costTotal
            );

            callDurationTotalForThisNumber += duration;
        }
        return callDurationTotalForThisNumber;
    }

    public List<String> getUniqueNumbersA(String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<String> listWithNumberA = new ArrayList<String>();
        listWithNumberA = callDataService.getUniqueLocalNumberAFromCalls(startTime, endTime);
        return listWithNumberA;
    }

    public List<Call> getCallsFromDBByNumbersA(String numberA, String year, String month) {
        Date endTime = getEndTimeDate(year, month);
        Date startTime = getStartTimeDate(year, month);
        List<Call> result = callDataService.getLocalCalls(numberA, startTime, endTime);
        return result;
    }

    public Double endOfTheHeaderPrint(PrintStream bw, Double costTotalForPeriod, Double costTotalForThisNumber) {

        String firstString = "";
        firstString = "----------|--------|----|-------|-----------|----------------------|-------|----";
        bw.println(firstString);

        firstString = " Всего " + round(costTotalForThisNumber, 2) + " секунд";
        bw.println(firstString);

        firstString = "--------------------------------------------------------------------------------";
        bw.println(firstString);

        bw.println("\r\n");
        bw.println("\r\n");
        costTotalForPeriod += costTotalForThisNumber;
        return costTotalForPeriod;
    }

}
