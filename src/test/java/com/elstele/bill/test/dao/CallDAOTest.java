package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.CallDAOImpl;
import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreators.CallTO;
import com.elstele.bill.reportCreators.CallsRequestParamTO;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import com.elstele.bill.test.builder.bean.CallBuilder;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
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
@WebAppConfiguration
public class CallDAOTest {

    private Date startDate;
    private Date endDate;
    private List<String> expectedList = new ArrayList<>();
    private List<String> localExpectedList = new ArrayList<>();
    private Call call;
    private Call call1;
    private Call call2;
    private int call2Id;

    @Autowired
    private CallDAOImpl callDAO;

    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setUp() {
        sessionFactory.getCurrentSession().createQuery("DELETE FROM Call ").executeUpdate();

        CallBuilder builder= new CallBuilder();

        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        call = builder.build()
                .withAonKat("1")
                .withCallDirectionId(11)
                .withCostPref(22.11f)
                .withCostRegular(99.33f)
                .withCostTotal(113.111f)
                .withDuration(1221)
                .withDvoCodeA("01")
                .withDvoCodeB("02")
                .withIkNum("123456789")
                .withInputTrunk("01")
                .withNumberA("1111111")
                .withNumberB("8888888")
                .randomNumberB()
                .withOutputTrunk("05")
                .withStartTime(startDate)
                .getRes();

        expectedList.add(call.getNumberA());
        callDAO.save(call);

        call1 = builder.build()
                .withAonKat("2")
                .withCallDirectionId(9)
                .withCostPref(22.11f)
                .withCostRegular(99.33f)
                .withCostTotal(113.111f)
                .withDuration(1221)
                .withDvoCodeA("02")
                .withDvoCodeB("03")
                .withIkNum("987654321")
                .withInputTrunk("02")
                .withNumberA("2222222")
                .withNumberB("9999999")
                .withOutputTrunk("014")
                .withStartTime(startDate)
                .getRes();

        expectedList.add(call1.getNumberA());
        callDAO.save(call1);

        call2 = builder.build().withNumberA("7895111").withNumberB("0977891234").withStartTime(startDate).getRes();

        localExpectedList.add(call2.getNumberA());
        call2Id = callDAO.create(call2);
    }

    @Test
    public void getCallsCountTest() {
        int actual = callDAO.getCallsCount();
        assertTrue(actual == 3);
    }

    @Test
    public void getCallsCountWithSearchValuestest() {
        CallsRequestParamTO searchByNumberA = new CallsRequestParamTO();
        searchByNumberA.setCallNumberA(call.getNumberA());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String timeRange = dateFormat.format(startDate) + " - " + dateFormat.format(endDate);

        int i = callDAO.getCallsCountWithSearchValues(searchByNumberA);
        assertTrue(i == 1);

        CallsRequestParamTO searchByNumberB = new CallsRequestParamTO();
        searchByNumberB.setCallNumberB(call1.getNumberB());
        int b = callDAO.getCallsCountWithSearchValues(searchByNumberB);
        assertTrue(b == 1);

        CallsRequestParamTO searchByStartDate = new CallsRequestParamTO();
        searchByStartDate.setStartDate(timeRange);
        int c = callDAO.getCallsCountWithSearchValues(searchByStartDate);
        assertTrue(c == 3);

        CallsRequestParamTO searchEndDate = new CallsRequestParamTO();
        searchEndDate.setEndDate(timeRange);
        int x = callDAO.getCallsCountWithSearchValues(searchEndDate);
        assertTrue(x == 3);

        CallsRequestParamTO searchByAll = new CallsRequestParamTO();
        searchByAll.setCallNumberA(call.getNumberA());
        searchByAll.setCallNumberB(call.getNumberB());
        searchByAll.setEndDate(timeRange);
        searchByAll.setEndDate(timeRange);
        int y = callDAO.getCallsCountWithSearchValues(searchByAll);
        assertTrue(y == 1);
    }

    @Test
    public void getCallsListTest() {
        List<Call> callList = callDAO.getCallsList(0, 0);
        assertTrue(callList.isEmpty());
    }

    @Test
    public void callsListSelectionBySearchTest() {
        CallsRequestParamTO callsRequestParamTO = new CallsRequestParamTO();
        callsRequestParamTO.setCallNumberA(call.getNumberA());
        callsRequestParamTO.setRows(1);
        callsRequestParamTO.setPage(0);
        List<Call> actual = callDAO.callsListSelectionBySearch(callsRequestParamTO);
        assertTrue(actual.contains(call));
/*
        actual = callDAO.callsListSelectionBySearch(1, 0, null, call1.getNumberB(), null, null);
        assertTrue(actual.contains(call1));

        actual = callDAO.callsListSelectionBySearch(100, 0, null, null, startDate, null);
        assertTrue(actual.size() == 3);

        actual = callDAO.callsListSelectionBySearch(100, 0, null, null, null, endDate);
        assertTrue(actual.size() == 3);

        actual = callDAO.callsListSelectionBySearch(100, 0, call.getNumberA(), call.getNumberB(), startDate, endDate);
        assertTrue(actual.contains(call));

        actual = callDAO.callsListSelectionBySearch(100, 0, call.getNumberA(), call1.getNumberB(), startDate, endDate);
        assertFalse(actual.contains(call));*/
    }

    @Test
    public void getUniqueNumberAFromCallsTest() {
        List<String> numbersList = callDAO.getUniqueNumberAFromCalls(startDate, endDate);
        assertEquals(numbersList, expectedList);
    }

    @Test
    public void getCallByNumberATest() {
        List<CallTO> callList;
        for (String numberA : expectedList) {
            callList = (callDAO.getCallByNumberA(numberA, startDate, endDate));
            assertNotNull(callList);
        }
    }

    @Test
    public void getUniqueNumberAFromCallsWithTrunkTest() {
        List<String> actual = callDAO.getUniqueNumberAFromCallsWithTrunk(startDate, endDate, ReportConstants.OUTPUT_TRUNK);
        assertTrue(actual.contains(call.getNumberA()));
    }

    @Test
    public void getCallByNumberAWithTrunkTest() {
        List<CallTO> actual = callDAO.getCallByNumberAWithTrunk(call.getNumberA(), startDate, endDate, ReportConstants.OUTPUT_TRUNK);
        assertTrue(actual.size() == 1);
        for (CallTO callTo : actual) {
            assertEquals(callTo.getNumberb(), call.getNumberB());
        }
    }

    @Test
    public void getUniqueLocalNumberAFromCallsTest() {
        List<String> stringList = callDAO.getUniqueLocalNumberAFromCalls(startDate, endDate);
        assertEquals(localExpectedList, stringList);
    }

    @Test
    public void getLocalCallsTest() {
        List<Call> callList;
        for (String numberA : expectedList) {
            callList = callDAO.getLocalCalls(numberA, startDate, endDate);
            assertTrue(callList.isEmpty());
        }
    }

    @Test
    public void getUnbilledCallsCountTest() {
        int actual = callDAO.getUnbilledCallsCount();
        assertTrue(actual == 1);
    }

    @Test
    public void getUnbilledCallIdsTest(){
        List<Integer> actual = callDAO.getUnbilledCallIds(10, 0);
        assertTrue(actual.size()==1);
        assertFalse(actual.isEmpty());
    }

    @Test
    public void getUnbilledCallIds1Test(){
        List<Integer> actual = callDAO.getUnbilledCallIds();
        assertTrue(actual.size()==1);
        assertFalse(actual.isEmpty());
    }

    @Test
    public void getYearsListTest(){
        List<String> actual = callDAO.getYearsList();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        int year = c.get(Calendar.YEAR);
        String yearNow= Integer.toString(year);
        assertTrue(actual.contains(yearNow));
    }

    @Test
    public void getCallIdsWithNullCostTotalTest(){
        List<Integer> actual = callDAO.getCallIdsWithNullCostTotal();
        assertTrue(actual.contains(call2Id));
    }

}
