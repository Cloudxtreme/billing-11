package com.elstele.bill.utils;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.datasrv.CallForCSVDataServiceImpl;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

public class ReportCreater {

    public void callLongReportCreate(String path, String fileName, CallForCSVDataService callForCSVDataService) throws IOException {
        try {

            Date tempStartTime = callForCSVDataService.getDateInterval();
            File file = new File(path + File.separator + tempStartTime.toString().substring(0, 7) + "_" + fileName + ".txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            PrintWriter bw = new PrintWriter(fw);
            Calendar c = Calendar.getInstance();
            c.setTime(tempStartTime);
            c.add(Calendar.MONTH, 1);
            c.set(Calendar.DAY_OF_MONTH, 1);
            Date endTime = c.getTime();
            c.add(Calendar.MONTH, -2);
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            Date startTime = c.getTime();

            List<CallForCSVForm> csvCallsWithUniqueNumber = new ArrayList<CallForCSVForm>();
            csvCallsWithUniqueNumber = callForCSVDataService.getUniqueNumberA(startTime, endTime);
            Double costTotalForPeriod = 0.0;
            for (CallForCSVForm callForCSVForm : csvCallsWithUniqueNumber) {
                String numberAShort = callForCSVForm.getNumberA().substring(1, 7);
                Double costTotalForThisNumber = 0.0;

                String firstString = "мНЛЕП РЕКЕТНМЮ, Я ЙНРНПНЦН ГБНМХКХ: " + numberAShort + "\r\n";
                String secondString = new String(firstString.getBytes("koi8-r"), "windows-1251");
                bw.write(secondString);

                firstString = "--------------------------------------------------------------------------------\r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                firstString = "          |             |             Кому звонили                 |       |    \r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                firstString = "   Дата   |  Время |Длит|------------------------------------------| Сумма |Зак.\r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                firstString = "          |             |  Код  | Телефон   | Город или страна     |       |    \r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                firstString = "----------|--------|----|-------|-----------|----------------------|-------|----\r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                List<CallForCSVForm> callForCSVFormListByNumberA = callForCSVDataService.getCallForCSVByNumberA(callForCSVForm.getNumberA(),startTime, endTime);
                for(CallForCSVForm callForCSVFormByNumberA : callForCSVFormListByNumberA){
                    String shortNumberB = callForCSVFormByNumberA.getNumberB().substring(0, callForCSVFormByNumberA.getDirPrefix().length());
                    bw.printf("%-18s|%4d|%-7s|%-11s|%-22s|%7.2f|\r\n",callForCSVFormByNumberA.getStartTime().toString(),callForCSVFormByNumberA.getDuration(),
                            callForCSVFormByNumberA.getDirPrefix(),shortNumberB,callForCSVFormByNumberA.getDirDescrpOrg(),callForCSVFormByNumberA.getCostCallTotal());
                    costTotalForThisNumber += Double.parseDouble(callForCSVFormByNumberA.getCostCallTotal());
                }

                firstString = "----------|--------|----|-------|-----------|----------------------|-------|----\r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                firstString = " Всего "+ Math.round(costTotalForThisNumber * 100.0) / 100.0 + "\r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                firstString = " В том числе НДС " + Math.round((costTotalForThisNumber * 0.2) * 100.0) / 100 + "\r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                firstString = "--------------------------------------------------------------------------------\r\n";
                secondString = new String(firstString.getBytes("koi8"), "windows-1251");
                bw.write(secondString);

                bw.write("\r\n");

                bw.write("\r\n");
                costTotalForPeriod += costTotalForThisNumber;

            }
            String firstString = " Итого "+ Math.round(costTotalForPeriod*100.0)/100 + "\r\n";
            String secondString = new String(firstString.getBytes("koi8-r"), "windows-1251");
            bw.write(secondString);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
