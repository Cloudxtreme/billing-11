package com.elstele.bill.test.datasrv;


import com.elstele.bill.dao.interfaces.CallDAO;
import com.elstele.bill.datasrv.impl.CallDataServiceImpl;
import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreators.CallTO;
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

    @Before
    public void setUp(){
        callDataService = new CallDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        startDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DATE, 1);
        endDate = c.getTime();

        callList = new ArrayList<>();
        call = new Call();
        call.setStatus(Status.ACTIVE);
        call.setCallDirectionId(11);
        call.setCostTotal(113.111f);
        call.setDuration(122l);
        call.setNumberA("1111111");
        call.setNumberB("8888888");
        call.setOutputTrunk("05");
        call.setStartTime(startDate);
        callList.add(call);

        call1 = new Call();
        call1.setStatus(Status.ACTIVE);
        call1.setCallDirectionId(9);
        call1.setCostTotal(113.111f);
        call1.setDuration(122l);
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
        call2.setNumberA("7895111");
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
    public void tearDown(){
        callDataService = null;
        callDAO = null;
    }

    @Test
    public void getNumberATest(){
        when(callDAO.getUniqueNumberAFromCalls(startDate, endDate)).thenReturn(numbersList);
        List<String> numberAList = callDataService.getUniqueNumberAFromCalls(startDate,endDate);
        assertEquals(numberAList, numberAList);
        assertTrue(numberAList.contains(call.getNumberA()));
    }

    @Test
    public void getCallByNumberATest(){
        when(callDAO.getCallByNumberA(call.getNumberA(), startDate, endDate)).thenReturn(callTOList);
        List<CallTO> callTOs = callDataService.getCallByNumberA(call.getNumberA(), startDate, endDate);
        assertEquals(callTOs, callTOList);
        assertTrue(callTOs.contains(callTO));
        assertTrue(callTOs.contains(callTO1));
    }

    @Test
    public void getLocalCallsTest(){
        when(callDAO.getLocalCalls(call.getNumberA(), startDate, endDate)).thenReturn(callList);
        List<Call> calls = callDataService.getLocalCalls(call.getNumberA(), startDate, endDate);
        assertEquals(calls, callList);
        assertTrue(calls.contains(call));
    }

    @Test
    public void getUniqueLocalNumberAFromCallsTest(){
        when(callDAO.getUniqueLocalNumberAFromCalls(startDate, endDate)).thenReturn(localCallsString);
        List<String> actualLocalNumbersList = callDataService.getUniqueLocalNumberAFromCalls(startDate,endDate);
        assertEquals(actualLocalNumbersList, localCallsString);
    }



}