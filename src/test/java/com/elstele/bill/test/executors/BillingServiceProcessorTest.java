package com.elstele.bill.test.executors;

import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.dao.interfaces.ServiceTypeDAO;
import com.elstele.bill.datasrv.interfaces.ServiceDataService;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.executors.BillingServiceProcessor;
import com.elstele.bill.Builders.bean.AccountBuilder;
import com.elstele.bill.Builders.bean.ServiceBuilder;
import com.elstele.bill.Builders.bean.ServiceTypeBuilder;
import com.elstele.bill.Builders.bean.TransactionBuilder;
import com.elstele.bill.Builders.form.AccountFormBuilder;
import com.elstele.bill.utils.Constants;
import org.apache.commons.lang.RandomStringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by ivan on 15/12/22.
 */

//TODO understood how to work with transactions and multithreading in tests

//@Ignore
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration(defaultRollback = false, transactionManager = "transactionManager")
public class BillingServiceProcessorTest {

    @Mock
    private ServiceDataService serviceDataService;

    @InjectMocks
    @Autowired
    private BillingServiceProcessor processor;

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private ServiceTypeDAO serviceTypeDAO;
    @Autowired
    private ServiceDAO serviceDAO;

    private List<Account> accounts;
    private Account accountSample;
    private static List<Integer> servisesIds = new ArrayList<>();

    private AccountBuilder accountBuilder;
    private TransactionBuilder transactionBuilder;
    private AccountFormBuilder accountFormBuilder;
    private ServiceBuilder serviceBuilder;
    private ServiceTypeBuilder stb;
    private Integer firstAccountId;
    private Integer firstServiceId;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    /**
     * Currently i couldn't find solution how to commit transaction after @Before execution, so first test here is just to prepare data for second
     * So this explain why assert(true) at the end.
     */
    @Test
    @Rollback(false)
    @Transactional
    public void a_testBillAllServises(){
        accountBuilder = new AccountBuilder();
        accountFormBuilder = new AccountFormBuilder();

        accounts = new ArrayList<>();

        Account ac1 = accountBuilder.build().withAccName("ACC_001").withBalance(20F).withAccType(Constants.AccountType.PRIVATE).withId(10).getRes();
        Account ac2 = accountBuilder.build().withAccName("ACC_002").withBalance(50.0F).withAccType(Constants.AccountType.LEGAL).withId(20).getRes();

        accountSample = ac1;
        accounts.add(ac1);
        accounts.add(ac2);

        stb = new ServiceTypeBuilder();

        String serviceTypeName1 = RandomStringUtils.randomAlphanumeric(6);
        String serviceTypeName2 = RandomStringUtils.randomAlphanumeric(6);


        ServiceType st1 = stb.build().withServiceType("INTERNET").withRandomAttribute()
                .withName(serviceTypeName1).withDescription("DDD1").withPrice(100F).getRes();
        ServiceType st2 = stb.build().withServiceType("INTERNET").withRandomAttribute()
                .withName(serviceTypeName2).withDescription("DDD2").withPrice(200F).getRes();


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

        servisesIds.add(srv1.getId());
        servisesIds.add(srv2.getId());
        servisesIds.add(srv3.getId());

        firstAccountId = ac1.getId();
        firstServiceId = srv1.getId();


        assertTrue(true);

    }

    @Test
    @Transactional
    @Rollback(false)
    public void b_testBillAllServises(){
        when(serviceDataService.listActiveServicesIds()).thenReturn(servisesIds);

        Integer resCount = processor.billAllServices();

        assertTrue(resCount.equals(3));
    }

    private void clearTables() {
        String clearServices = String.format("delete from Services");
        Query query = sessionFactory.getCurrentSession().createQuery(clearServices);
        query.executeUpdate();
    }


}
