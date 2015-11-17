package com.elstele.bill.reportCreators.reportParent;


import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import com.elstele.bill.utils.CallTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class GeneralReportCreator {

    final public static Logger log = LogManager.getLogger(GeneralReportCreator.class);


    public PrintStream createFileForWriting(ReportDetails reportDetails) {
        createMainFolder(reportDetails.getPath());
        String pathDir = createFolderWithDate(reportDetails.getPath(), reportDetails.getYear(), reportDetails.getMonth());
        File file = new File(pathDir + File.separator + reportDetails.getYear() + "-" + reportDetails.getMonth() + "_" + reportDetails.getReportName() + ".txt");
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
        String firstString;

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
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, Integer.parseInt(month));
        String endDay = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startTime = year + "/" + month + "/" + endDay + " 23:59";
        try {
            return simpleDateFormat.parse(startTime);
        } catch (ParseException e) {
            log.error(e);
            return null;
        }
    }

    public Date getStartTimeDate(String year, String month) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startTime = year + "/" + month + "/" + ReportConstants.START_DAY + " 00:00";
        try {
            return simpleDateFormat.parse(startTime);
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

    public Double costTotalForThisCallNumberOperation(List<CallTO> callListByNumberA) {
        Double costTotalForThisNumber = 0.0;
        for (CallTO call : callListByNumberA) {
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
