package com.elstele.bill.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarUtils {
    public Integer decrimentMonthNumber(int decriment) {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) - decriment;
    }

    public String getYearMonthAfterDecriment(int decriment) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -decriment);
        String yyyy_MM = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1);
        return yyyy_MM;
    }

    public Integer getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public Integer getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public Integer getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public List<Date> getMonthYearListBetweenTwoDates(Date startDate, Date endDate) {
        List<Date> dateList = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        while (c.getTimeInMillis() <= endDate.getTime()) {
            dateList.add(c.getTime());
            c.add(Calendar.MONTH, 1);
        }
        return dateList;
    }

    public String getMonthNameByNumber(int monthNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, monthNumber);
        String monthName = new SimpleDateFormat("MMM").format(calendar.getTime());
        return monthName;
    }
}
