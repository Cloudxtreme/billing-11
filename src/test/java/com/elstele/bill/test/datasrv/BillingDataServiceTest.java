package com.elstele.bill.test.datasrv;

import com.elstele.bill.dao.impl.AccountDAOImpl;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.dao.interfaces.ServiceTypeDAO;
import com.elstele.bill.datasrv.impl.AccountDataServiceImpl;
import com.elstele.bill.datasrv.interfaces.BillingDataService;
import com.elstele.bill.datasrv.interfaces.ServiceDataService;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.executors.BillingServiceProcessor;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.test.builder.bean.AccountBuilder;
import com.elstele.bill.test.builder.bean.ServiceBuilder;
import com.elstele.bill.test.builder.bean.ServiceTypeBuilder;
import com.elstele.bill.test.builder.bean.TransactionBuilder;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class BillingDataServiceTest {

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private ServiceTypeDAO serviceTypeDAO;
    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private TransactionDataService transactionDataService;

    @Autowired
    private BillingDataService billingDataService;

    private List<Account> accounts;
    private Account accountSample;

    private AccountBuilder accountBuilder;
    private TransactionBuilder transactionBuilder;
    private AccountFormBuilder accountFormBuilder;
    private ServiceBuilder serviceBuilder;
    private ServiceTypeBuilder stb;

    private Integer firstAccountId;
    private Integer firstServiceId;

    @Before

    public void setUp(){

        accountBuilder = new AccountBuilder();
        accountFormBuilder = new AccountFormBuilder();

        accounts = new ArrayList<Account>();

        Account ac1 = accountBuilder.build().withAccName("ACC_001").withBalance(20F).withAccType(Constants.AccountType.PRIVATE).withId(10).getRes();
        Account ac2 = accountBuilder.build().withAccName("ACC_002").withBalance(50.0F).withAccType(Constants.AccountType.LEGAL).withId(20).getRes();

        accountSample = ac1;
        accounts.add(ac1);
        accounts.add(ac2);

        stb = new ServiceTypeBuilder();

        ServiceType st1 = stb.build().withServiceType("INTERNET").withRandomAttribute()
                .withName("NNN1").withDescription("DDD1").withPrice(100F).getRes();
        ServiceType st2 = stb.build().withServiceType("INTERNET").withRandomAttribute()
                .withName("NNN2").withDescription("DDD2").withPrice(200F).getRes();


        serviceBuilder = new ServiceBuilder();
        Service srv1 = serviceBuilder.build().randomService().withAccount(ac1).withServiceType(st1).getRes();
        Service srv2 = serviceBuilder.build().randomService().withAccount(ac1).withServiceType(st2).getRes();

        Service srv3 = serviceBuilder.build().randomService().withAccount(ac2).withServiceType(st2).getRes();

        Set<Service> setForFirstAccount = new HashSet<>();
        setForFirstAccount.add(srv1);
        setForFirstAccount.add(srv2);
        Set<Service> setForSecondAccount = new HashSet<>();
        setForSecondAccount.add(srv3);

        ac1.setAccountServices(setForFirstAccount);
        ac2.setAccountServices(setForSecondAccount);

        accountDAO.create(ac1);
        accountDAO.create(ac2);

        serviceTypeDAO.create(st1);
        serviceTypeDAO.create(st2);

        serviceDAO.create(srv1);
        serviceDAO.create(srv2);
        serviceDAO.create(srv3);


        firstAccountId = ac1.getId();
        firstServiceId = srv1.getId();

    }


    @Test

    public void testBillServices(){

        Integer transactionId = billingDataService.createTransactionAndDecreaseBalance(firstServiceId);

        Account ac1FromBase = accountDAO.getById(firstAccountId);
        assertTrue(ac1FromBase.getCurrentBalance().equals(-80F));

        TransactionForm  tf = transactionDataService.getTransactionById(transactionId);
        assertTrue(tf.getAccount().getId().equals(firstAccountId));
    }




}
