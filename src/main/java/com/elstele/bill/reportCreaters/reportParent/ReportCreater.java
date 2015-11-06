package com.elstele.bill.reportCreaters.reportParent;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.CallTransformerDir;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportCreater {

    @Autowired
    CallForCSVDataService callForCSVDataService;

    @Autowired
    CallDataService callDataService;
    final public static Logger log = LogManager.getLogger(ReportCreater.class);


    public PrintStream createFileForWriting(String path, String fileName, String year, String month) {
        createMainFolder(path);
        String pathDir = createFolderWithDate(path, year, month);
        File file = new File(pathDir + File.separator + year + "-" + month + "_" + fileName + ".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintStream bw = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, false)), true, "cp1251");
            log.info("File " + file.getName() + " is successful created for writing");
            return bw;
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }

    public void mainHeaderPrint(PrintStream bw, String numberA) {
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


    public Date getEndTimeDate(String year, String month) {
        String endDay = "";
        if ((month.equalsIgnoreCase("01")) || (month.equalsIgnoreCase("03")) || (month.equalsIgnoreCase("05")) || (month.equalsIgnoreCase("07")) || (month.equalsIgnoreCase("08"))
                || (month.equalsIgnoreCase("10")) || (month.equalsIgnoreCase("12"))){
            endDay = "31";
        }
        if(month.equalsIgnoreCase("04")|| month.equalsIgnoreCase("06")||month.equalsIgnoreCase("09")||month.equalsIgnoreCase("11")){
            endDay="30";
        }
        if(month.equalsIgnoreCase("02")&& (Integer.parseInt(year)%4) == 0){
            endDay="29";
        }
        if(month.equalsIgnoreCase("02")&& (Integer.parseInt(year)%4) != 0){
            endDay="28";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startTime = year + "/" + month + "/" + endDay + " 23:59";
        try {
            Date startTimeInDateFormat = simpleDateFormat.parse(startTime);
            return startTimeInDateFormat;
        } catch (ParseException e) {
            log.error(e);
            return null;
        }
    }

    public Date getStartTimeDate(String year, String month) {
        String startDay = "01";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startTime = year + "/" + month + "/" + startDay + " 00:00";
        try {
            Date startTimeInDateFormat = simpleDateFormat.parse(startTime);
            return startTimeInDateFormat;
        } catch (ParseException e) {
            log.error(e);
            return null;
        }
    }

    public Double costTotalForThisNumberOperation(List<CallForCSV> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (CallForCSV callForCSV : callListByNumberA) {
            Double costTotal = Double.parseDouble(callForCSV.getCostCallTotal());
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }

    public Double costTotalForThisCallNumberOperation(PrintStream bw, List<CallTransformerDir> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (CallTransformerDir call : callListByNumberA) {
            Double costTotal = (double) call.getCosttotal();
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }

    public void createMainFolder(String path) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            boolean fileMet = false;
            try {
                fileDir.mkdir();
                fileMet = true;
            } catch (SecurityException e) {
                log.error(e);
            }
            if (fileMet) {
                log.info("File dir " + fileDir.getAbsolutePath() + " is created");
            }
        }
    }

    public String createFolderWithDate(String path, String year, String month) {
        File directory = new File(path + File.separator + year + "-" + month);
        if (!directory.exists()) {
            boolean fileMet = false;
            try {
                directory.mkdir();
                fileMet = true;
            } catch (SecurityException e) {
                log.error(e);
            }
            if (fileMet) {
                log.info("File directory " + directory.getAbsolutePath() + " is created");
            }
        }
        return directory.getPath();
    }

    public double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
