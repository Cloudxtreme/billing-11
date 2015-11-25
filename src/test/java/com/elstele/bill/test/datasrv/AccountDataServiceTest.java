package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.AccountDAOImpl;
import com.elstele.bill.datasrv.implementes.AccountDataServiceImpl;
import com.elstele.bill.domain.Account;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.utils.Constants.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountDataServiceTest {
    @Mock
    private AccountDAOImpl accountDAO;

    @InjectMocks
    private AccountDataServiceImpl accountDataService;

    private List<Account> accounts;
    private Account accountSample;

    @Before
    public void setUp(){
        accountDataService = new AccountDataServiceImpl();
        MockitoAnnotations.initMocks(this);

        accounts = new ArrayList<Account>();

        Account ac1 = new Account();
        ac1.setAccountName("ACC_001");
        ac1.setCurrentBalance(20F);
        ac1.setAccountType(Constants.AccountType.PRIVATE);
        ac1.setStatus(Status.ACTIVE);
        ac1.setId(10);

        Account ac2 = new Account();
        ac2.setAccountName("ACC_002");
        ac2.setCurrentBalance(50.0F);
        ac2.setAccountType(Constants.AccountType.LEGAL);
        ac2.setStatus(Status.ACTIVE);
        ac2.setId(20);

        accountSample = ac1;
        accounts.add(ac1);
        accounts.add(ac2);

    }

    @After
    public void tearDown() {
        accountDataService = null;
        accountDAO = null;
    }

    @Test
    public void testFetchingListOfAccounts(){
        when(accountDAO.getAccountList()).thenReturn(accounts);

        AccountForm af1 = new AccountForm();
        af1.setAccountName("ACC_001");
        af1.setCurrentBalance(20F);
        af1.setAccountType(Constants.AccountType.PRIVATE);
        af1.setStatus(Status.ACTIVE);
        af1.setId(10);

        AccountForm af2 = new AccountForm();
        af2.setAccountName("ACC_002");
        af2.setCurrentBalance(50.0F);
        af2.setAccountType(Constants.AccountType.LEGAL);
        af2.setStatus(Status.ACTIVE);
        af2.setId(20);

        List<AccountForm> formList = accountDataService.getAccountsList();
        assertTrue(formList.contains(af1));
        assertTrue(formList.contains(af2));
    }


    @Test
    public void testFetchingAccountById(){
        when(accountDAO.getById(10)).thenReturn(accountSample);
        AccountForm af1 = new AccountForm();
        af1.setAccountName("ACC_001");
        af1.setCurrentBalance(20F);
        af1.setAccountType(Constants.AccountType.PRIVATE);
        af1.setStatus(Status.ACTIVE);
        af1.setId(10);

        AccountForm target = accountDataService.getAccountById(10);
        assertTrue(target.equals(af1));

        AccountForm targetNull = accountDataService.getAccountById(0);
        assertNull(targetNull);
    }

}
