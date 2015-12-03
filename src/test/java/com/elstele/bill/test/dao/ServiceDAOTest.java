package com.elstele.bill.test.dao;


import com.elstele.bill.dao.impl.*;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ServiceDAOTest {

    @Autowired
    ServiceDAOImpl serviceDAO;
    @Autowired
    ServiceTypeDAOImpl serviceTypeDAO;
    @Autowired
    AccountDAOImpl accountDAO;

    List<Service> expected;

    @Before
    public void setUp(){
        expected = new ArrayList<>();

        Service service = new Service();
        ServiceType type1 = new ServiceType();
        serviceTypeDAO.save(type1);
        service.setServiceType(type1);
        Account ac1 = new Account();
        ac1.setAccountName("ACC_001");
        ac1.setCurrentBalance(20F);
        ac1.setAccountType(Constants.AccountType.PRIVATE);
        ac1.setStatus(Status.ACTIVE);
        accountDAO.save(ac1);
        service.setAccount(ac1);
        expected.add(service);
        serviceDAO.save(service);

        Service service1 = new Service();
        service1.setAccount(ac1);
        service1.setServiceType(type1);

        expected.add(service1);
        serviceDAO.save(service1);
    }

    @After
    public void tearDown(){
        expected = null;
    }

    @Test
    public void listServicesTest(){
        List<Service> actual = serviceDAO.listServices();
        assertEquals(actual,expected);
    }

}
