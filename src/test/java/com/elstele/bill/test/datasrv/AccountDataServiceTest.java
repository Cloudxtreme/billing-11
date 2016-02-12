package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.AccountDAOImpl;
import com.elstele.bill.datasrv.impl.AccountDataServiceImpl;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServicePhone;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.test.builder.bean.AccountBuilder;
import com.elstele.bill.test.builder.bean.ServiceBuilder;
import com.elstele.bill.test.builder.bean.ServiceInternetBuilder;
import com.elstele.bill.test.builder.bean.ServicePhoneBuilder;
import com.elstele.bill.test.builder.form.AccountFormBuilder;
import com.elstele.bill.utils.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private AccountBuilder accountBuilder;
    private AccountFormBuilder accountFormBuilder;

    private Service serviceMarker;
    private ServicePhone servicePhone;
    private ServiceInternet serviceInternet;
    private AccountForm accountForm;

    @Before
    public void setUp() {
        accountDataService = new AccountDataServiceImpl();
        MockitoAnnotations.initMocks(this);
        accountBuilder = new AccountBuilder();
        accountFormBuilder = new AccountFormBuilder();

        accounts = new ArrayList<Account>();

        Account ac1 = accountBuilder.build().withAccName("ACC_001").withBalance(20F).withAccType(Constants.AccountType.PRIVATE).withId(10).getRes();
        Account ac2 = accountBuilder.build().withAccName("ACC_002").withBalance(50.0F).withAccType(Constants.AccountType.LEGAL).withId(20).getRes();

        accountSample = ac1;
        accounts.add(ac1);
        accounts.add(ac2);

        Account account = accountBuilder.build().withId(1).withAccName("ACC_001").withFIO("test").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).getRes();
        accountForm = accountFormBuilder.build().withId(1).withAccName("ACC_001").withFIO("test").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).getRes();
        ServiceBuilder smb = new ServiceBuilder();
        serviceMarker = smb.build().randomService().withAccount(account).getRes();

        ServicePhoneBuilder spb = new ServicePhoneBuilder();
        servicePhone = spb.build().randomService().withAccount(account).getRes();

        ServiceInternetBuilder sib = new ServiceInternetBuilder();
        serviceInternet = sib.build().randomService().withAccount(account).getRes();
    }

    @After
    public void tearDown() {
        accountDataService = null;
        accountDAO = null;
    }

    @Test
    public void testFetchingListOfAccounts() {
        when(accountDAO.getAccountList()).thenReturn(accounts);

        AccountForm af1 = accountFormBuilder.build().withAccName("ACC_001").withBalance(20F).withAccType(Constants.AccountType.PRIVATE).withId(10).getRes();
        AccountForm af2 = accountFormBuilder.build().withAccName("ACC_002").withBalance(50.0F).withAccType(Constants.AccountType.LEGAL).withId(20).getRes();

        List<AccountForm> formList = accountDataService.getAccountsList();
        assertTrue(formList.contains(af1));
        assertTrue(formList.contains(af2));
    }


    @Test
    public void testFetchingAccountById() {
        when(accountDAO.getById(10)).thenReturn(accountSample);
        AccountForm af1 = accountFormBuilder.build().withAccName("ACC_001").withBalance(20F).withAccType(Constants.AccountType.PRIVATE).withId(10).getRes();

        AccountForm target = accountDataService.getAccountById(10);
        assertTrue(target.equals(af1));

        AccountForm targetNull = accountDataService.getAccountById(0);
        assertNull(targetNull);
    }

    @Test
    public void searchAccountsTest() {
        Set<AccountForm> accountFormList = new HashSet<>();
        List<Service> serviceList = new ArrayList<>();

        serviceList.add(serviceMarker);
        serviceList.add(servicePhone);
        serviceList.add(serviceInternet);

        accountDataService.addFormToListWithFIO(accountFormList, accounts);
        accountDataService.addFormWithPhoneNumberToList(accountFormList, serviceList);
        accountDataService.addFormWithLoginToList(accountFormList, serviceList);

        assertTrue(accountFormList.contains(accountForm));
    }


}
