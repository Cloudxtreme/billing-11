package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.USDRateDAOImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class USDRateDAOTest {

    @Autowired
    USDRateDAOImpl usdRateDAO;
    private Date date;
    private Double value;

    @Before
    public void setUp() {
        date = new Date();
        value = 20.00;
    }

    @After
    public void tearDown() {
        date = null;
        value = null;
    }

    @Test
    public void setUSDRateValueTest() {
        usdRateDAO.setUSDRateValue(date, value);
    }
}
