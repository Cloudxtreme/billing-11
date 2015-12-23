package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.interfaces.CallDAO;
import com.elstele.bill.datasrv.impl.CallDataServiceImpl;
import com.elstele.bill.domain.Call;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.reportCreators.CallTO;
import com.elstele.bill.reportCreators.CallsRequestParamTO;
import com.elstele.bill.utils.Enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CallDataServiceTest {

    @Mock
    private CallDAO callDAO;

    @InjectMocks
    CallDataServiceImpl callDataService;

    private List<String> numbersList;
    private List<CallTO> callTOList;
    private List<Call> callList;
    private List<String> localCallsString;
    private Date endDate;
    private Date startDate;
    private CallTO callTO;
    private CallTO callTO1;
    private Call call;
    private Call call1;
    private Call call2;
    private CallForm callForm;

    @Before
    public void setUp() {
        callDataService = new CallDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        //TODO make with builders
        callForm = new CallForm();
        callForm.setStatus(Status.ACTIVE);
        callForm.setCallDirectionId(11);
        callForm.setCostTotal(113.111f);
        callForm.setDuration(122l);
        callForm.setId(1);
        callForm.setNumberA("1111111");
        callForm.setNumberB("8888888");
        callForm.setOutputTrunk("05");
        callForm.setStartTime(startDate);


        callList = new ArrayList<>();
        call = new Call();
        call.setStatus(Status.ACTIVE);
        call.setCallDirectionId(11);
        call.setCostTotal(113.111f);
        call.setDuration(122l);
        call.setId(1);
        call.setNumberA("1111111");
        call.setNumberB("8888888");
        call.setOutputTrunk("05");
        call.setStartTime(startDate);

        call1 = new Call();
        call1.setStatus(Status.ACTIVE);
        call1.setCallDirectionId(9);
        call1.setCostTotal(113.111f);
        call1.setDuration(122l);
        call1.setId(2);
        call1.setNumberA("2222222");
        call1.setNumberB("9999999");
        call1.setOutputTrunk("014");
        call1.setStartTime(startDate);

        numbersList = new ArrayList<>();
        numbersList.add(call1.getNumberA());
        numbersList.add(call.getNumberA());
        callList.add(call);
        callList.add(call1);

        call2 = new Call();
        call2.setStartTime(startDate);
        call2.setNumberA("0937895111");
        call2.setId(3);
        localCallsString = new ArrayList<>();
        localCallsString.add(call2.getNumberA());

        callTO1 = new CallTO();
        callTO1.setCosttotal(113.111f);
        callTO1.setDuration(BigInteger.valueOf(112l));
        callTO1.setNumberb("8888888");

        callTO = new CallTO();
        callTO1.setCosttotal(113.111f);
        callTO1.setDuration(BigInteger.valueOf(112l));
        callTO1.setNumberb("9999999");

        callTOList = new ArrayList<>();
        callTOList.add(callTO);
        callTOList.add(callTO1);
    }

    @After
    public void tearDown() {
        callDataService = null;
        callDAO = null;
        callList = null;
    }

    @Test
    public void addCallsTest() {
        CallForm callFormExpected = new CallForm();
        callFormExpected.setId(1);
        callFormExpected.setNumberB("8888888");
        when(callDAO.create(call)).thenReturn(1);

        CallForm callForm = new CallForm();
        callForm.setNumberB("8888888");
        callForm.setId(1);
        callDataService.addCalls(callForm);
        assertTrue(callForm.equals(callFormExpected));
    }

    @Test
    public void getCallsCountTest() {
        when(callDAO.getCallsCount()).thenReturn(3);
        int count = callDataService.getCallsCount();
        assertTrue(count == 3);
    }

    @Test
    public void getCallsCountWithSearchValuesTest() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String timeRange = dateFormat.format(startDate) + " - " + dateFormat.format(endDate);

        CallsRequestParamTO searchByNumberA = new CallsRequestParamTO();
        searchByNumberA.setCallNumberA(call.getNumberA());
        int expectedCount;
        when(callDAO.getCallsCountWithSearchValues(searchByNumberA)).thenReturn(expectedCount = 1);
        int countByA = callDataService.getCallsCountWithSearchValues(searchByNumberA);
        assertEquals(countByA, expectedCount);

        CallsRequestParamTO searchByNumberB = new CallsRequestParamTO();
        searchByNumberB.setCallNumberB(call1.getNumberB());
        when(callDAO.getCallsCountWithSearchValues(searchByNumberB)).thenReturn(expectedCount = 1);
        int countByB = callDataService.getCallsCountWithSearchValues(searchByNumberB);
        assertEquals(countByB, expectedCount);

        CallsRequestParamTO searchByStartDate = new CallsRequestParamTO();
        searchByStartDate.setStartDate(timeRange);
        when(callDAO.getCallsCountWithSearchValues(searchByStartDate)).thenReturn(expectedCount = 1);
        int countByStartDate = callDataService.getCallsCountWithSearchValues(searchByStartDate);
        assertEquals(countByStartDate, expectedCount);

        CallsRequestParamTO searchByEndDate = new CallsRequestParamTO();
        searchByEndDate.setEndDate(timeRange);
        when(callDAO.getCallsCountWithSearchValues(searchByEndDate)).thenReturn(expectedCount = 1);
        int countByEndDate = callDataService.getCallsCountWithSearchValues(searchByEndDate);
        assertEquals(countByEndDate, expectedCount);
    }

    @Test
    public void getCallsListTest() {
        when(callDAO.getCallsList(10, 10)).thenReturn(callList);
        List<CallForm> actualList = callDataService.getCallsList(10, 2);
        assertTrue(actualList.contains(callForm));
    }

    @Test
    public void callsListSelectionBySearchTest() {
        List<Call> listExpected = new ArrayList<>();
        listExpected.add(call);
        when(callDAO.callsListSelectionBySearch(10, 10, call.getNumberA(), call.getNumberB(), startDate, endDate)).thenReturn(listExpected);
        List<CallForm> actualList = callDataService.callsListSelectionBySearch(10, 2, call.getNumberA(), call.getNumberB(), startDate, endDate);
        assertTrue(actualList.contains(callForm));
    }

    @Test
    public void getUniqueNumberAFromCallsTest() {
        when(callDAO.getUniqueNumberAFromCalls(startDate, endDate)).thenReturn(numbersList);
        List<String> numberAList = callDataService.getUniqueNumberAFromCalls(startDate, endDate);
        assertEquals(numberAList, numberAList);
        assertTrue(numberAList.contains(call.getNumberA()));
    }

    @Test
    public void getCallByNumberATest() {
        when(callDAO.getCallByNumberA(call.getNumberA(), startDate, endDate)).thenReturn(callTOList);
        List<CallTO> callTOs = callDataService.getCallByNumberA(call.getNumberA(), startDate, endDate);
        assertEquals(callTOs, callTOList);
        assertTrue(callTOs.contains(callTO));
        assertTrue(callTOs.contains(callTO1));
    }

    @Test
    public void getUniqueNumberAFromCallsWithTrunkTest() {
        List<String> expectedList = new ArrayList<>();
        expectedList.add(call.getNumberA());
        when(callDAO.getUniqueNumberAFromCallsWithTrunk(startDate, endDate, call.getOutputTrunk())).thenReturn(expectedList);
        List<String> actualList = callDataService.getUniqueNumberAFromCallsWithTrunk(startDate, endDate, call.getOutputTrunk());
        assertEquals(actualList, expectedList);
    }

    @Test
    public void getCallByNumberAWithTrunkTest() {
        List<CallTO> expectedList = new ArrayList<>();
        CallTO callTO = new CallTO();
        callTO.setNumberb(call.getNumberB());
        callTO.setCosttotal(call.getCostTotal());
        callTO.setDuration(BigInteger.valueOf(call.getDuration()));
        expectedList.add(callTO);

        when(callDAO.getCallByNumberAWithTrunk(call.getNumberA(), startDate, endDate, call.getOutputTrunk())).thenReturn(expectedList);
        List<CallTO> actualList = callDataService.getCallByNumberAWithTrunk(call.getNumberA(), startDate, endDate, call.getOutputTrunk());
        assertEquals(actualList, expectedList);
    }

    @Test
    public void getUniqueLocalNumberAFromCallsTest() {
        when(callDAO.getUniqueLocalNumberAFromCalls(startDate, endDate)).thenReturn(localCallsString);
        List<String> actualLocalNumbersList = callDataService.getUniqueLocalNumberAFromCalls(startDate, endDate);
        assertEquals(actualLocalNumbersList, localCallsString);
    }

    @Test
    public void getLocalCallsTest() {
        when(callDAO.getLocalCalls(call.getNumberA(), startDate, endDate)).thenReturn(callList);
        List<Call> calls = callDataService.getLocalCalls(call.getNumberA(), startDate, endDate);
        assertEquals(calls, callList);
        assertTrue(calls.contains(call));
    }

    @Test
    public void getYearsListTest() {
        List<String> expectedList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        int year = c.get(Calendar.YEAR);
        String yearNow = Integer.toString(year);
        expectedList.add(yearNow);

        when(callDAO.getYearsList()).thenReturn(expectedList);

        List<String> actualList = callDataService.getYearsList();
        assertEquals(actualList, expectedList);
    }

    @Test
    public void getUnbilledCallsCountTest() {
        int expectedCount = 0;
        when(callDAO.getUnbilledCallsCount()).thenReturn(expectedCount = 1);
        int actualCount = callDataService.getUnbilledCallsCount();
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void getUnbilledCallsIdListTest() {
        List<Integer> expectedIdList = new ArrayList<>();
        expectedIdList.add(call2.getId());
        when(callDAO.getUnbilledCallIds(1, 0)).thenReturn(expectedIdList);
        List<Integer> actualIdList = callDataService.getUnbilledCallsIdList(1,0);
        assertEquals(actualIdList,expectedIdList);
    }
}
