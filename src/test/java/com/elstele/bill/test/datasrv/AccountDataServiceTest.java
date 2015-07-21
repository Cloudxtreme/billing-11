package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.AccountDAOImpl;
import com.elstele.bill.datasrv.AccountDataService;
import com.elstele.bill.datasrv.AccountDataServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountDataServiceTest {
    @Mock
    private AccountDAOImpl accountDAO;

    @InjectMocks
    private AccountDataServiceImpl accountDataService;

    @Before
    public void setUp(){
        accountDataService = new AccountDataServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() {
        accountDataService = null;
        accountDAO = null;
    }

    //TODO continue here with tests
}
