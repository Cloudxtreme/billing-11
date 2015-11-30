package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.CallDAOImpl;
import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreators.CallTO;
import com.elstele.bill.reportCreators.CallsRequestParamTO;
import com.elstele.bill.reportCreators.reportConstants.ReportConstants;
import com.elstele.bill.utils.Constants.Constants;
import com.elstele.bill.utils.Enums.Status;
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

    @Before
    public void setUp() {
        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        call = new Call();
        call.setStatus(Status.ACTIVE);
        call.setAonKat("1");
        call.setCallDirectionId(11);
        call.setCostPref(22.11f);
        call.setCostRegular(99.33f);
        call.setCostTotal(113.111f);
        call.setDuration(122l);
        call.setDvoCodeA("01");
        call.setDvoCodeB("02");
        call.setIkNum("123456789");
        call.setInputTrunk("01");
        call.setNumberA("1111111");
        call.setNumberB("8888888");
        call.setOutputTrunk("05");
        call.setStartTime(startDate);

        expectedList.add(call.getNumberA());
        callDAO.save(call);

        call1 = new Call();
        call1.setStatus(Status.ACTIVE);
        call1.setAonKat("2");
        call1.setCallDirectionId(9);
        call1.setCostPref(22.11f);
        call1.setCostRegular(99.33f);
        call1.setCostTotal(113.111f);
        call1.setDuration(122l);
        call1.setDvoCodeA("02");
        call1.setDvoCodeB("03");
        call1.setIkNum("987654321");
        call1.setInputTrunk("02");
        call1.setNumberA("2222222");
        call1.setNumberB("9999999");
        call1.setOutputTrunk("014");
        call1.setStartTime(startDate);

        expectedList.add(call1.getNumberA());
        callDAO.save(call1);

        call2 = new Call();
        call2.setNumberA("7895111");
        call2.setNumberB("0977891234");
        call2.setStartTime(startDate);

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
        List<Call> actual = callDAO.callsListSelectionBySearch(1, 0, call.getNumberA(), null, null, null);
        assertTrue(actual.contains(call));

        actual = callDAO.callsListSelectionBySearch(1, 0, null, call1.getNumberB(), null, null);
        assertTrue(actual.contains(call1));

        actual = callDAO.callsListSelectionBySearch(100, 0, null, null, startDate, null);
        assertTrue(actual.size() == 3);

        actual = callDAO.callsListSelectionBySearch(100, 0, null, null, null, endDate);
        assertTrue(actual.size() == 3);

        actual = callDAO.callsListSelectionBySearch(100, 0, call.getNumberA(), call.getNumberB(), startDate, endDate);
        assertTrue(actual.contains(call));

        actual = callDAO.callsListSelectionBySearch(100, 0, call.getNumberA(), call1.getNumberB(), startDate, endDate);
        assertFalse(actual.contains(call));
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
