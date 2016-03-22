package com.elstele.bill.reportCreators.dateparser;

import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateReportParser {
    final public static Logger LOGGER = LogManager.getLogger(DateReportParser.class);

    public static Date parseEndTime(ReportDetails reportDetails) {
        Integer yearInt = Integer.parseInt(reportDetails.getYear());
        Integer monthInt = Integer.parseInt(reportDetails.getMonth()) - 1;
        Integer startDayInt = Integer.parseInt(ReportConstants.START_DAY);
        Calendar calendar = new GregorianCalendar(yearInt, monthInt, startDayInt);
        calendar.set(Calendar.YEAR, yearInt);
        calendar.set(Calendar.MONTH, monthInt);
        String endDay = Integer.toString(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String endTime = reportDetails.getYear() + "/" + reportDetails.getMonth() + "/" + endDay + " 23:59";
        try {
            Date startTimeInDateFormat = simpleDateFormat.parse(endTime);
            LOGGER.info("End time is " + startTimeInDateFormat);
            return startTimeInDateFormat;
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public static Date parseStartTime(ReportDetails reportDetails) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String startTime = reportDetails.getYear() + "/" + reportDetails.getMonth() + "/" + ReportConstants.START_DAY + " 00:00";
        try {
            Date startTimeInDateFormat = simpleDateFormat.parse(startTime);
            LOGGER.info("Start time is " + startTimeInDateFormat);
            return startTimeInDateFormat;
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public static Date getNowDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date());
        try {
            return dateFormat.parse(formattedDate);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            return new Date();
        }
    }

    public static Date getePrevDayDate(Date newDateFromFile){
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDateFromFile);
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
