package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.utils.CallTransformerDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ReportCreater {

    @Autowired
    CallForCSVDataService callForCSVDataService;

    @Autowired
    CallDataService callDataService;


    public PrintStream createFileForWriting(String path, String fileName) throws IOException {
        Date tempStartTime = callForCSVDataService.getDateInterval();
        createMainFolder(path);
        String pathDir = createFolderWithDate(path, tempStartTime);
        File file = new File(pathDir + File.separator + tempStartTime.toString().substring(0, 7) + "_" + fileName + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintStream bw = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, false)), true, "cp1251");
        return bw;
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
            Double costTotal = (double)call.getCosttotal();
            costTotalForThisNumber += costTotal;
        }
        return costTotalForThisNumber;
    }

    public void createMainFolder(String path){
        File fileDir = new File(path);
        if(!fileDir.exists()){
            boolean fileMet = false;
            try{
                fileDir.mkdir();
                fileMet = true;
            }catch (SecurityException e ){
                System.out.println(e.toString());
            }
            if(fileMet){
                System.out.println("File dir is created");
            }
        }
    }

    public String createFolderWithDate(String path, Date tempStartTime){
        File directory = new File(path +File.separator +  tempStartTime.toString().substring(0,7));
        if(!directory.exists()){
            boolean fileMet = false;
            try{
                directory.mkdir();
                fileMet = true;
            }catch (SecurityException e ){
                System.out.println(e.toString());
            }
            if(fileMet){
                System.out.println("File directory is created");
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
