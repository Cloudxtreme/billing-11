package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.interfaces.CallDAO;
import com.elstele.bill.datasrv.impl.CallDataServiceImpl;
import com.elstele.bill.domain.Call;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.reportCreators.CallTO;
import com.elstele.bill.reportCreators.CallsRequestParamTO;
import com.elstele.bill.test.builder.bean.CallBuilder;
import com.elstele.bill.test.builder.bean.CallRequestParamTOBuilder;
import com.elstele.bill.test.builder.bean.CallTOBuilder;
import com.elstele.bill.test.builder.form.CallFormBuilder;
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
    private CallForm callForm1;
    private CallForm callForm2;
    private CallsRequestParamTO fullParam;
    private CallsRequestParamTO emptyParam;
    private CallRequestParamTOBuilder callRequestParamTOBuilder;

    @Before
    public void setUp() {
        callDataService = new CallDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        CallFormBuilder builder = new CallFormBuilder();
        CallBuilder callBuilder = new CallBuilder();
        CallTOBuilder callTOBuilder = new CallTOBuilder();
        callRequestParamTOBuilder = new CallRequestParamTOBuilder();

        call = callBuilder.build().withCallDirectionId(11).withCostTotal(113.111f).withDuration(122).withId(1).withNumberA("1111111")
                .withNumberB("8888888").withOutputTrunk("05").withStartTime(startDate).getRes();
        call1 = callBuilder.build().withCallDirectionId(9).withCostTotal(113.111f).withDuration(122).withId(2).withNumberA("2222222")
                .withNumberB("9999999").withOutputTrunk("014").withStartTime(startDate).getRes();
        call2 = callBuilder.build().withStartTime(startDate).withNumberA("0937895111").withId(3).getRes();

        callForm = builder.build().withCallDirectionId(11).withCostTotal(113.111f).withDuration(122).withId(1).withNumberA("1111111")
                .withNumberB("8888888").withOutputTrunk("05").withStartTime(startDate).getRes();
        callForm1 = builder.build().withCallDirectionId(9).withCostTotal(113.111f).withDuration(122).withId(2).withNumberA("2222222")
                .withNumberB("9999999").withOutputTrunk("014").withStartTime(startDate).getRes();
        callForm2 = builder.build().withStartTime(startDate).withNumberA("0937895111").withId(3).getRes();

        callList = new ArrayList<>();
        numbersList = new ArrayList<>();
        numbersList.add(call1.getNumberA());
        numbersList.add(call.getNumberA());
        callList.add(call);
        callList.add(call1);

        localCallsString = new ArrayList<>();
        localCallsString.add(call2.getNumberA());

        callTO1 =callTOBuilder.build().withCostTotal(113.111f).withDuration(BigInteger.valueOf(112l)).withNumberB("8888888").getRes();
        callTO = callTOBuilder.build().withCostTotal(113.111f).withDuration(BigInteger.valueOf(112l)).withNumberB("9999999").getRes();

        callTOList = new ArrayList<>();
        callTOList.add(callTO);
        callTOList.add(callTO1);

        fullParam = callRequestParamTOBuilder.build()
                .withNumberA(call.getNumberA())
                .withNumberB(call.getNumberB())
                .withStartDate("2016/01/05 00:00 - 2016/01/13 00:00")
                .withEndDate("2016/01/05 00:00 - 2016/01/13 00:00")
                .withSelectedTime("2016/01/05 00:00 - 2016/01/13 00:00")
                .withPageResult(1)
                .getRes();

        emptyParam = callRequestParamTOBuilder.build()
                .withPageResult(1)
                .withNumberA("")
                .withNumberB("")
                .withSelectedTime("")
                .getRes();
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
        List<Integer> actualIdList = callDataService.getUnbilledCallsIdList(1, 0);
        assertEquals(actualIdList,expectedIdList);
    }

    @Test
    public void determineTotalPagesForOutputTest(){
        when(callDAO.getCallsCount()).thenReturn(100);
        when(callDAO.getCallsCountWithSearchValues(fullParam)).thenReturn(5);

        int result = callDataService.determineTotalPagesForOutput(fullParam);
        assertTrue(result == 5);

        int resultWithEmptyObject = callDataService.determineTotalPagesForOutput(emptyParam);
        assertTrue(resultWithEmptyObject == 100);
    }

    @Test
    public void getCallsList(){
        List<Call> emptyListCall = new ArrayList<>();
        emptyListCall.add(call2);
        when(callDAO.callsListSelectionBySearch(emptyParam)).thenReturn(emptyListCall);
        when(callDAO.callsListSelectionBySearch(fullParam)).thenReturn(callList);

        List<CallForm> actualListFull = callDataService.getCallsList(fullParam);
        assertTrue(actualListFull.contains(callForm));
        assertTrue(actualListFull.contains(callForm1));

        List<CallForm> actualEmptyList = callDataService.getCallsList(emptyParam);
        assertTrue(actualEmptyList.contains(callForm2));
    }
}
