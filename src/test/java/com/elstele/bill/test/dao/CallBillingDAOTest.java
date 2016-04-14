package com.elstele.bill.test.dao;

import com.elstele.bill.billparts.CallBillRule;
import com.elstele.bill.billparts.CallDirection;
import com.elstele.bill.dao.impl.CallBillingDAOImpl;
import org.hibernate.SessionFactory;
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
public class CallBillingDAOTest {

    @Autowired
    CallBillingDAOImpl callBillingDAO;

    @Autowired
    SessionFactory sessionFactory;

    private String numberB;
    private String numberB1;
    private String numberB2;
    private Date validFrom;

    @Before
    public void setUp() {
        numberB = "7850102";
        numberB1 = "0978901122";
        numberB2 = "0671123344";

        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, Calendar.AUGUST, 15);

        validFrom = calendar.getTime();
    }

    @Test
    public void getCallDirectionTest() {

        CallDirection callDirection = callBillingDAO.getCallDirection(numberB, validFrom);
        assertTrue(callDirection.getTarif() == null);

        callDirection = callBillingDAO.getCallDirection(numberB1, validFrom);
        assertFalse(callDirection.getTarif() != null);

        callDirection = callBillingDAO.getCallDirection(numberB2, validFrom);
        assertTrue(callDirection.getTarif() != null);
    }

    @Test
    public void getCallBillingRuleTest() {
        CallDirection callDirection = callBillingDAO.getCallDirection(numberB2, validFrom);
        List<CallBillRule> actualList = callBillingDAO.getCallBillingRule(callDirection.getPref_profile(), validFrom);
        assertTrue(actualList != null);
    }

    @Test
    public void getUsdRateForCallTest() {
        Calendar c = Calendar.getInstance();
        c.set(2015, Calendar.AUGUST, 26, 0, 0);
        Date date = c.getTime();
        float actual = callBillingDAO.getUsdRateForCall(date);
        assertTrue(actual > 0);
    }
}
