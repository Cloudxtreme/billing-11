package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.CallForCSVDAOImpl;
import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CallForCSVDAOTest {
    @Autowired
    CallForCSVDAOImpl callForCSVDAO;

    private List<String> expectedNumberAList;
    private Date startDate;
    private Date endDate;
    private CallForCSV call1;
    private CallForCSV call2;
    private CallForCSV call3;
    private CallForCSV call4;

    @Before
    public void setUp() {
        expectedNumberAList = new ArrayList<>();

        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        call1 = new CallForCSV();
        call1.setProvider("1");
        call1.setCostCallTotal("100");
        call1.setDirPrefix("00504");
        call1.setNumberA("7890102");
        call1.setStartTime(new Date());

        call2 = new CallForCSV();
        call2.setProvider("1");
        call2.setCostCallTotal("200");
        call2.setNumberA("7890012");
        call2.setStartTime(new Date());

        call3 = new CallForCSV();
        call3.setProvider("2");
        call3.setNumberA("7890099");
        call3.setDirPrefix("0565");
        call3.setStartTime(new Date());

        call4 = new CallForCSV();
        call4.setProvider("2");
        call4.setNumberA("7890222");
        call4.setStartTime(new Date());

        expectedNumberAList.add(call1.getNumberA());
        expectedNumberAList.add(call2.getNumberA());
        expectedNumberAList.add(call3.getNumberA());
        expectedNumberAList.add(call4.getNumberA());

        callForCSVDAO.save(call1);
        callForCSVDAO.save(call2);
        callForCSVDAO.save(call3);
        callForCSVDAO.save(call4);
    }

    @After
    public void tearDown() {
        expectedNumberAList = null;
        call1 = null;
        call2 = null;
        call3 = null;
        call4 = null;
    }

    @Test
    public void getUniqueNumberATest() {
        List<String> actual = callForCSVDAO.getUniqueNumberA(startDate, endDate);
        assertTrue(actual.contains(call1.getNumberA()));
        assertTrue(actual.contains(call2.getNumberA()));
        assertFalse(actual.contains(call3.getNumberA()));
        assertNotEquals(actual, expectedNumberAList);
    }

    @Test
    public void getUniqueNumberAWithProviderTest() {
        List<String> actual = callForCSVDAO.getUniqueNumberAWithProvider(startDate, endDate, ReportConstants.UKRTEL_PROVIDER);
        assertTrue(actual.contains(call1.getNumberA()));
        assertTrue(actual.contains(call2.getNumberA()));
        assertFalse(actual.contains(call3.getNumberA()));
    }

    @Test
    public void getCallForCSVByNumberATest() {
        List<CallForCSV> actual = callForCSVDAO.getCallForCSVByNumberA(call1.getNumberA(), startDate, endDate);
        assertTrue(actual.contains(call1));

        List<CallForCSV> actual1 = callForCSVDAO.getCallForCSVByNumberA(call3.getNumberA(), startDate, endDate);
        assertTrue(actual1.contains(call3));
    }

    @Test
    public void getCallForCSVByNumberAWithProviderTest() {
        List<CallForCSV> actual = callForCSVDAO.getCallForCSVByNumberAWithProvider(call1.getNumberA(), startDate, endDate, ReportConstants.UKRTEL_PROVIDER);
        assertTrue(actual.contains(call1));

        List<CallForCSV> actual1 = callForCSVDAO.getCallForCSVByNumberAWithProvider(call3.getNumberA(), startDate, endDate, ReportConstants.VEGA_PROVIDER);
        assertTrue(actual1.contains(call3));
    }

    @Test
    public void getDescriptionFromDirections() {
        String actualDescription = callForCSVDAO.getDescriptionFromDirections(call1.getDirPrefix());
        assertEquals(actualDescription, "Гондурас");

        String actualDescription1 = callForCSVDAO.getDescriptionFromDirections(call3.getDirPrefix());
        assertEquals(actualDescription1, "Днепропетровская обл.");
    }
}
