package com.elstele.bill.utils;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportCreater {

    @Autowired
    CallForCSVDataService callForCSVDataService;

    public void callLongReportCreate() {
        String tempStartTime = callForCSVDataService.getDateInterval();
        String startDay = "1";
        String endDay="01";
        String year = tempStartTime.substring(0,5);
        String month = tempStartTime.substring(5,7);
        String monthStart = "";
        String monthEnd = " ";
        if(month.equals("01")){
            startDay = "31";
            monthStart = "12";
            monthEnd = "02";
        }if(month.equals("02")){
            startDay = "31";
            monthStart = "01";
            monthEnd = "03";
        }if ( month.equals("03")&&(Integer.parseInt(year) % 4) == 0){
            startDay = "29";
            monthStart = "02";
            monthEnd = "04";
        }if( month.equals("03")&&(Integer.parseInt(year) % 4)!= 0){
            startDay = "28";
            monthStart = "02";
            monthEnd = "04";
        }if(month.equals("04")){
            startDay = "31";
            monthStart = "03";
            monthEnd = "05";
        }


        if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
            endDay = "31";
        }
        if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
            endDay = "30";
        }
        if (month.equals("02") && (Integer.parseInt(year) % 4) == 0) {
            endDay = "29";
        }
        if (month.equals("02") && (Integer.parseInt(year) % 4) != 0) {
            endDay = "28";
        }

        String startTime = year + "-" + month + "-" + startDay + " 00:00:00";
        String endTime = year + "-" + month + "-" + endDay + " 23:59:59";
        List<CallForCSVForm> csvCallsWithUniqueNumber = new ArrayList<CallForCSVForm>();
        csvCallsWithUniqueNumber = callForCSVDataService.getUniqueNumberA(startTime, endTime);

    }
}
