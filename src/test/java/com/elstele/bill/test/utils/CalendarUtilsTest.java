package com.elstele.bill.test.utils;

import com.elstele.bill.utils.CalendarUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class CalendarUtilsTest {
    @InjectMocks
    CalendarUtils calendarUtils;

    @Before
    public void setUp() {
        calendarUtils = new CalendarUtils();
    }

    @Test
    public void decrimentMonthNumberTest() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);

        int result = calendarUtils.decrimentMonthNumber(1);
        assertTrue(result == month - 1);
    }

    @Test
    public void getYearMonthAfterDecrimentTest() {
        String result = calendarUtils.getYearMonthAfterDecriment(1);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        String expected = year + "-" + month;
        assertTrue(result.equals(expected));
    }

    @Test
    public void getCurrentMonthTest() {
        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int actual = calendarUtils.getCurrentMonth();
        assertEquals(actual, currentMonth);
    }

    @Test
    public void getCurrentYearTest() {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int actual = calendarUtils.getCurrentYear();
        assertEquals(actual, currentYear);
    }

    @Test
    public void getLastDayOfMonthTest() {
        int actual = calendarUtils.getLastDayOfMonth(2015, 12);
        assertTrue(actual == 31);
    }

    @Test
    public void getMonthYearListBetweenTwoDatesTest() {
        List<Date> expected = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date start = calendar.getTime();

        expected.add(start);
        calendar.add(Calendar.MONTH, 1);
        expected.add(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        expected.add(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        expected.add(calendar.getTime());
        calendar.add(Calendar.MONTH, 1);
        Date end = calendar.getTime();
        expected.add(end);

        List<Date> actual = calendarUtils.getMonthYearListBetweenTwoDates(start, end);
        assertEquals(actual, expected);
    }

    @Test
    public void getMonthNameByNumberTest(){
        String expected = "Апр.";
        String actual = calendarUtils.getMonthNameByNumber(3);
        assertEquals(actual, expected);
    }
}
