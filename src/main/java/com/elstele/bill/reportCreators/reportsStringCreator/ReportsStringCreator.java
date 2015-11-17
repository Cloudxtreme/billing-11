package com.elstele.bill.reportCreators.reportsStringCreator;

import com.elstele.bill.utils.CallTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsStringCreator {
    private  Double costTotalForThisNumber = 0.0;
    private static List<String> stringList = new ArrayList<>();


    public  List<String> stringCreate(String numberA, List<CallTO> callListByNumberA){
        header(numberA);
        callData(callListByNumberA);
        footerCreate();
        return stringList;

    }

    public  void header(String numberA){
        String numberAShort = numberA.substring(1, numberA.length());
        String firstString;
        firstString = "Номер телефона, с которого звонили: " + numberAShort;
        stringList.add(firstString);
        firstString = "--------------------------------------------------------------------------------";
        stringList.add(firstString);
        firstString = "          |             |             Кому звонили                 |       |    ";
        stringList.add(firstString);
        firstString = "   Дата   |  Время |Длит|------------------------------------------| Сумма |Зак.";
        stringList.add(firstString);
        firstString = "          |             |  Код  | Телефон   | Город или страна     |       |    ";
        stringList.add(firstString);
        firstString = "----------|--------|----|-------|-----------|----------------------|-------|----";
        stringList.add(firstString);
    }

    public  void callData(List<CallTO> callListByNumberA){
        for (CallTO callTO : callListByNumberA) {
            String numberB = callTO.getNumberb();
            String duration = callTO.getDuration().toString();
            String dirPrefix = callTO.getPrefix();
            String descrOrg = callTO.getDescription();
            Double costTotal = (double) callTO.getCosttotal();
            Date startTimeVal = callTO.getStarttime();
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String reportDate = df.format(startTimeVal);
            String shortNumberB = callTO.getNumberb().substring(dirPrefix.length(), numberB.length());
            String result = String.format("%-18s|%-4s|%-7s|%-11s|%-22s|%7.2f|\r\n", reportDate, duration, dirPrefix, shortNumberB, descrOrg, costTotal);
            stringList.add(result);
            costTotalForThisNumber += costTotal;
        }
    }

    private  void footerCreate(){
        String firstString;
        firstString = "----------|--------|----|-------|-----------|----------------------|-------|----";
        stringList.add(firstString);

        firstString = " Всего " + round(costTotalForThisNumber, 2);
        stringList.add(firstString);

        firstString = " В том числе НДС " + round(costTotalForThisNumber * 0.2, 2);
        stringList.add(firstString);

        firstString = "--------------------------------------------------------------------------------";
        stringList.add(firstString);
        stringList.add("\r\n");
        stringList.add("\r\n");
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
