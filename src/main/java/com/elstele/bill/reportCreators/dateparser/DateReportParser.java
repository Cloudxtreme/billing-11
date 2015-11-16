package com.elstele.bill.reportCreators.dateparser;

import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateReportParser {
    final public static Logger log = LogManager.getLogger(DateReportParser.class);
    private String year;
    private String month;

    public DateReportParser(String year, String month) {
        this.year = year;
        this.month = month;
    }

    public Date parseEndTime() {
        Integer yearInt = Integer.parseInt(year);
        Integer monthInt = Integer.parseInt(month);
        Integer startDayInt = Integer.parseInt(ReportConstants.startDay);
        Calendar calendar = new GregorianCalendar(yearInt, monthInt, startDayInt);
        calendar.set(Calendar.YEAR, yearInt);
        calendar.set(Calendar.MONTH, monthInt);
        String endDay = Integer.toString(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String endTime = year + "/" + month + "/" + endDay + " 23:59";
        try {
            Date startTimeInDateFormat = simpleDateFormat.parse(endTime);
            log.info("End time is "+ startTimeInDateFormat );
            return startTimeInDateFormat;
        } catch (ParseException e) {
            log.error(e);
            return null;
        }
    }

    public Date parseStartTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startTime = year + "/" + month + "/" + ReportConstants.startDay + " 00:00";
        try {
            Date startTimeInDateFormat = simpleDateFormat.parse(startTime);
            log.info("Start time is "+ startTimeInDateFormat);
            return startTimeInDateFormat;
        } catch (ParseException e) {
            log.error(e);
            return null;
        }
    }
}
