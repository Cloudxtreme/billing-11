package com.elstele.bill.csvFileParser.csvLineParsers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVFileLineParser {

    final static Logger LOGGER = LogManager.getLogger(CSVFileLineParser.class);

    public static Date startTimeHandling(String call_start) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = call_start.substring(6, 10);
        String month = call_start.substring(3, 5);
        String day = call_start.substring(0, 2);
        String time = call_start.substring(10, 18);
        Date startTime = new Date();
        try {
            startTime = sdf.parse(year + "-" + month + "-" + day + " " + time);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }
        return startTime;
    }

    public static String costWithNDS(String costWithoutNDS) {
        if (costWithoutNDS.indexOf(",") == 0) {
            costWithoutNDS = "0" + costWithoutNDS;
        }
        return Double.toString(Double.parseDouble(costWithoutNDS) * 1.2);
    }

    public boolean checkWithRegExp(String strToCheck, String strPattern) {
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strToCheck);
        return m.matches();
    }

    public Date startTimeHandlingUkrNet(String call_start) {
        String year = call_start.substring(0, 4);
        String month = call_start.substring(4, 6);
        String day = call_start.substring(6, 8);
        String hour = call_start.substring(8, 10);
        String min = call_start.substring(10, 12);
        String sec = call_start.substring(12, 14);
        String startTimeStr = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = new Date();
        try {
            startTime = sdf.parse(startTimeStr);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage());
        }
        return startTime;
    }


}
