package com.elstele.bill.utils;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.domain.CallForCSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportCreater {

    @Autowired
    CallForCSVDataService callForCSVDataService;

    @Autowired
    CallDataService callDataService;

    public void callLongReportCreate(String path, String fileName) throws IOException {
        try {
            PrintStream bw = createFileForWriting(path, fileName);
            filePrintingCreate(bw, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PrintStream createFileForWriting(String path, String fileName) throws IOException {
        Date tempStartTime = callForCSVDataService.getDateInterval();
        File file = new File(path + File.separator + tempStartTime.toString().substring(0, 7) + "_" + fileName + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintStream bw = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, false)), true, "cp1251");
        return bw;
    }

    public void filePrintingCreate(PrintStream bw, String fileName) {
        Double costTotalForPeriod = 0.0;
        String provider = "";
        String outputTrunk = "";
        if (fileName.equalsIgnoreCase("longReportRAVega")) {
            provider = "2";
        }
        if (fileName.equalsIgnoreCase("longReportRAUkrTel")) {
            provider = "1";
        }
        if (fileName.equalsIgnoreCase("longReportVega") || fileName.equalsIgnoreCase("longReport")) {
            outputTrunk = "05";
        }

        List<String> listWithNumberA = getUniqueNumbersA(provider, fileName);
        for (String numberA : listWithNumberA) {
            String firstString = "";
            if (fileName.equalsIgnoreCase("longReportVega") || fileName.equalsIgnoreCase("longReport")) {
                List<Call> callsListByNumberA = getCallByNumbersA(numberA, fileName);
                mainHeaderprint(bw, numberA);
                Double costTotalForThisNumber = 0.0;
                costTotalForThisNumber = callDataPrint(bw, callsListByNumberA, fileName);
                costTotalForPeriod = endOfTheHeaderPrint(bw, costTotalForPeriod, costTotalForThisNumber);
            } else {
                List<CallForCSV> callForCSVListByNumberA = getCallForCSVByNumbersA(numberA, provider, fileName);
                mainHeaderprint(bw, numberA);
                Double costTotalForThisNumber = 0.0;
                costTotalForThisNumber = callForCSVDataPrint(bw, callForCSVListByNumberA, fileName);
                costTotalForPeriod = endOfTheHeaderPrint(bw, costTotalForPeriod, costTotalForThisNumber);
            }
        }
        String firstString = " Итого " + round(costTotalForPeriod, 2);
        bw.println(firstString);
        bw.close();
    }

    public List<String> getUniqueNumbersA(String provider, String fileName) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<String> listWithNumberA = new ArrayList<String>();

        if (fileName.equalsIgnoreCase("longReportVega") || fileName.equalsIgnoreCase("longReport")) {
            listWithNumberA = callDataService.getUniqueNumberAFromCallsWithTrunk(startTime, endTime, "05");
        }
        if (fileName.equalsIgnoreCase("longReportRA")) {
            listWithNumberA = callForCSVDataService.getUniqueNumberA(startTime, endTime);
        } else {
            listWithNumberA = callForCSVDataService.getUniqueNumberAWithProvider(startTime, endTime, provider);
        }
        return listWithNumberA;
    }

    public List<CallForCSV> getCallForCSVByNumbersA(String numberA, String provider, String fileName) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<CallForCSV> result = new ArrayList();
        if (fileName.equals("longReportRA") && !fileName.contains("RAUkrTel")) {
            result = callForCSVDataService.getCallForCSVByNumberA(numberA, startTime, endTime);
        } else {
            result = callForCSVDataService.getCallForCSVByNumberAWithProvider(numberA, startTime, endTime, provider);
        }
        return result;
    }

    public List<Call> getCallByNumbersA(String numberA, String fileName) {
        Date tempStartTime = getTempStartTime();
        Date endTime = getEndTimeDate(tempStartTime);
        Date startTime = getStartTimeDate(tempStartTime);
        List<Call> result = callDataService.getCallByNumberAWithTrunk(numberA, startTime, endTime, "05");
        return result;
    }

    public Double callForCSVDataPrint(PrintStream bw, List<CallForCSV> callForCSVListByNumberA, String fileName) {
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
            } else {

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

    public Double callDataPrint(PrintStream bw, List<Call> callListByNumberA, String fileName) {
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

    public void mainHeaderprint(PrintStream bw, String numberA) {
        String numberAShort = numberA.substring(1, numberA.length());
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

    public Double endOfTheHeaderPrint(PrintStream bw, Double costTotalForPeriod, Double costTotalForThisNumber) {

        String firstString = "";
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

    public Date getTempStartTime() {
        Date tempStartTime = callForCSVDataService.getDateInterval();
        return tempStartTime;
    }

    public Date getEndTimeDate(Date tempStartTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(tempStartTime);
        c.add(Calendar.MONTH, 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        Date endTime = c.getTime();
        return endTime;
    }

    public Date getStartTimeDate(Date tempStartTime) {
        Calendar c = Calendar.getInstance();
        c.setTime(tempStartTime);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date startTime = c.getTime();
        return startTime;
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
