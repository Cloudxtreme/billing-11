package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.CallDAOImpl;
import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreators.CallTO;
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
        call2.setStartTime(startDate);

        localExpectedList.add(call2.getNumberA());
        callDAO.save(call2);
    }

    @Test
    public void testGetDistinctNumbers() {
        List<String> numbersList = callDAO.getUniqueNumberAFromCalls(startDate, endDate);
        assertEquals(numbersList, expectedList);
    }

    @Test
    public void testGetCalls() {
        List<CallTO> callList;
        for (String numberA : expectedList) {
            callList = (callDAO.getCallByNumberA(numberA, startDate, endDate));
            assertNotNull(callList);
        }
    }

    @Test
    public void getLocalCallsTest(){
        List<Call> callList;
        for(String numberA : expectedList){
            callList = callDAO.getLocalCalls(numberA, startDate , endDate);
            assertTrue(callList.isEmpty());
        }
    }

    @Test
    public void getUniqueLocalNumberAFromCallsTest(){
        List<String> stringList  = callDAO.getUniqueLocalNumberAFromCalls(startDate, endDate);
        assertEquals(localExpectedList, stringList);
    }

    @Test
    public void getCallsListTest(){
        List<Call> callList = callDAO.getCallsList(0, 0);
        assertTrue(callList.isEmpty());
    }

}
