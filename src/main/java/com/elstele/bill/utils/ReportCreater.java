package com.elstele.bill.utils;

import com.elstele.bill.dao.CallForCSVDAO;
import com.elstele.bill.dao.CallForCSVDAOImpl;
import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.datasrv.CallForCSVDataServiceImpl;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportCreater {

    public void callLongReportCreate(String path, String fileName, CallForCSVDataService callForCSVDataService) throws IOException {

        try {
            PrintStream bw = createFileForWriting(path, fileName, callForCSVDataService);
            filePrintingCreate(bw, callForCSVDataService, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static PrintStream createFileForWriting(String path, String fileName, CallForCSVDataService callForCSVDataService) throws IOException {
        Date tempStartTime = callForCSVDataService.getDateInterval();
        File file = new File(path + File.separator + tempStartTime.toString().substring(0, 7) + "_" + fileName + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintStream bw = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, true)), true, "cp1251");
        return bw;
    }

    public static void filePrintingCreate(PrintStream bw, CallForCSVDataService callForCSVDataService, String fileName) {
        Double costTotalForPeriod = 0.0;
        String provider = "";
        if (fileName.equalsIgnoreCase("longReport") || fileName.equalsIgnoreCase("longReportRA")) {
            provider = "2";
        }
        if (fileName.equalsIgnoreCase("longReportRAUkrTel")) {
            provider = "1";
        }
        List<String> csvCallsWithUniqueNumberList = getUniqueNumbersA(callForCSVDataService, provider);
        for (String numberA : csvCallsWithUniqueNumberList) {
            String firstString = "";
            List<CallForCSV> callForCSVListByNumberA = getCallForCSVByNumbersA(callForCSVDataService, numberA);
            mainHeaderprint(bw, numberA, firstString, fileName);
            Double costTotalForThisNumber = 0.0;
            costTotalForThisNumber = callForCSVDataPrint(bw, callForCSVListByNumberA, fileName);
            costTotalForPeriod = endOfTheHeaderPrint(bw, firstString, costTotalForPeriod, costTotalForThisNumber);
        }
        String firstString = " Итого " + round(costTotalForPeriod, 2);
        bw.println(firstString);
        bw.close();
    }

    public static void mainHeaderprint(PrintStream bw, String numberA, String firstString, String fileName) {
        String numberAShort = "";

        if (fileName.equalsIgnoreCase("longReport")|| fileName.equalsIgnoreCase("longReportRA")) {
            numberAShort = numberA.substring(6, 12);
        }
        if (fileName.equalsIgnoreCase("longReportRAUkrTel")) {
            numberAShort = numberA.substring(1, numberA.length());
        }

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

    public static Double callForCSVDataPrint(PrintStream bw, List<CallForCSV> callForCSVListByNumberA, String fileName) {
        Double costTotalForThisNumber = 0.0;

        for (CallForCSV callForCSVByNumberA : callForCSVListByNumberA) {


            String numberB = callForCSVByNumberA.getNumberB();
            String duration = callForCSVByNumberA.getDuration();
            String dirPrefix = callForCSVByNumberA.getDirPrefix();
            String dirPrefixCutted = dirPrefix.substring(2, dirPrefix.length());
            String descrOrg = callForCSVByNumberA.getDirDescrpOrg();
            Double costTotal = Double.parseDouble(callForCSVByNumberA.getCostCallTotal());

            Date startTimeVal = callForCSVByNumberA.getStartTime();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(startTimeVal);

            String shortNumberB = callForCSVByNumberA.getNumberB().substring(dirPrefix.length(), numberB.length());

            if (fileName.equalsIgnoreCase("longReport")) {

                bw.printf("%-18s|%-4s|%-7s|%-11s|%-22s|%7.2f|\r\n",
                        reportDate,
                        duration,
                        dirPrefixCutted,
                        shortNumberB,
                        descrOrg,
                        costTotal
                );
            }
            if (fileName.equalsIgnoreCase("longReportRAUkrTel")|| fileName.equalsIgnoreCase("longReportRA")) {

                bw.printf("%-18s|%-4s|%-7s|%-11s|%-22s|%7.2f|\r\n",
                        reportDate,
                        duration,
                        dirPrefix,
                        shortNumberB,
                        descrOrg,
                        costTotal
                );
            }


            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }

    public static Double endOfTheHeaderPrint(PrintStream bw, String firstString, Double costTotalForPeriod, Double costTotalForThisNumber) {

        firstString = "----------|--------|----|-------|-----------|----------------------|-------|----";
        bw.println(firstString);

        firstString = " Всего " + round(costTotalForThisNumber, 2);
        bw.println(firstString);

        firstString = " В том числе НДС " + round(costTotalForThisNumber * 0.2, 2);
        bw.println(firstString);

        firstString = "--------------------------------------------------------------------------------";
        bw.println(firstString);

        bw.println("\r\n");
        bw.println("\r\n");
        costTotalForPeriod += costTotalForThisNumber;
        return costTotalForPeriod;
    }

    public static List<String> getUniqueNumbersA(CallForCSVDataService callForCSVDataService, String provider) {
        Date tempStartTime = getTempStartTime(callForCSVDataService);
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<String> csvCallsWithUniqueNumber = callForCSVDataService.getUniqueNumberA(startTime, endTime, provider);
        return csvCallsWithUniqueNumber;
    }

    public static List<CallForCSV> getCallForCSVByNumbersA(CallForCSVDataService callForCSVDataService, String numberA) {
        Date tempStartTime = getTempStartTime(callForCSVDataService);
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<CallForCSV> result = callForCSVDataService.getCallForCSVByNumberA(numberA, startTime, endTime);
        return result;
    }

    public static Date getTempStartTime(CallForCSVDataService callForCSVDataService) {
        Date tempStartTime = callForCSVDataService.getDateInterval();
        return tempStartTime;
    }

    public static Date getEndTimeDate(Date tempStartTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(tempStartTime);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date endTime = c.getTime();
        return endTime;
    }

    public static Date getStartTimeDate(Date tempStartTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(tempStartTime);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date startTime = c.getTime();
        return startTime;
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
