package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.USDRateDAOImpl;
import com.elstele.bill.dao.interfaces.USDRateDAO;
import com.elstele.bill.datasrv.impl.USDRateDataServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class USDRateDataServiceTest {
    @Mock
    USDRateDAO dao;

    @InjectMocks
    USDRateDataServiceImpl dataService;

    @Before
    public void setUp(){
        dataService = new USDRateDataServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getXMLUSDRateTest(){

    }
}
