package com.elstele.bill.test.reports;

import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


import java.util.Calendar;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
public class DateParserTest {

    private ReportDetails reportDetails;

    @Before
    public void setUp() {
        reportDetails = new ReportDetails();
        reportDetails.setYear("2000");
        reportDetails.setMonth("10");
        reportDetails.setReportName("longReport");
        reportDetails.setPath(ReportConstants.REPORTS_PATH);
    }

    @Test
    public void startTimeParseTest() {
        Date start = DateReportParser.parseStartTime(reportDetails);
        Calendar cal = Calendar.getInstance();
        cal.setTime(start);
        int year = cal.get(Calendar.YEAR);

        //Calendar.Month counts months from 0;
        int month = cal.get(Calendar.MONTH);
        assertTrue(year == 2000);
        assertTrue(month == 9);
    }

    @Test
    public void endTimeParserTest(){
        Date end = DateReportParser.parseEndTime(reportDetails);
        Calendar cal = Calendar.getInstance();
        cal.setTime(end);
        int year = cal.get(Calendar.YEAR);

        //Calendar.Month counts months from 0;
        int month = cal.get(Calendar.MONTH);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        assertTrue(year == 2000);
        assertTrue(month == 9);
        assertTrue(hour == 11);
        assertTrue(minute == 59);
    }
}
