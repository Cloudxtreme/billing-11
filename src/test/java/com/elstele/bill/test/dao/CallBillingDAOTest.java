package com.elstele.bill.test.dao;

import com.elstele.bill.billparts.CallBillRule;
import com.elstele.bill.billparts.CallDirection;
import com.elstele.bill.dao.impl.CallBillingDAOImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
public class CallBillingDAOTest {

    @Autowired
    CallBillingDAOImpl callBillingDAO;

    private String numberB;
    private String numberB1;
    private String numberB2;

    @Before
    public void setUp() {
        numberB = "7850102";
        numberB1 = "0978901122";
        numberB2 = "0631123344";
    }

    @Test
    public void getCallDirectionTest() {
        CallDirection callDirection = callBillingDAO.getCallDirection(numberB);
        assertTrue(callDirection.getTarif() == null);

        callDirection = callBillingDAO.getCallDirection(numberB1);
        assertTrue(callDirection.getTarif() != null);

        callDirection = callBillingDAO.getCallDirection(numberB2);
        assertFalse(callDirection.getTarif() == null);
    }

    @Test
    public void getCallBillingRuleTest() {
        CallDirection callDirection = callBillingDAO.getCallDirection(numberB1);
        List<CallBillRule> actualList = callBillingDAO.getCallBillingRule(callDirection.getPref_profile());
        assertTrue(actualList != null);
    }

    @Test
    public void getUsdRateForCallTest() {
        Calendar c = Calendar.getInstance();
        c.set(2015, 8, 26, 0, 0);
        Date date = c.getTime();
        float actual = callBillingDAO.getUsdRateForCall(date);
        assertTrue(actual > 0);
    }
}
