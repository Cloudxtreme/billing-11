package com.elstele.bill.test.dao;

import com.elstele.bill.dao.impl.AccountDAOImpl;
import com.elstele.bill.domain.Account;
import com.elstele.bill.test.builder.bean.AccountBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountDAOTest {

    @Autowired
    private AccountDAOImpl dao;
    @Autowired
    private SessionFactory sessionFactory;

    Account ac1;
    Account ac2;
    private Integer id1;
    private Integer id2;

    @Before
    public void setUp(){
        /*String clearAccounts = String.format("delete from Account");
        Query query = sessionFactory.getCurrentSession().createQuery(clearAccounts);
        query.executeUpdate();
        String clearAddress = String.format("delete from Address");
        query = sessionFactory.getCurrentSession().createQuery(clearAddress);
        query.executeUpdate();
        String clearStreets = String.format("delete from Street");
        query = sessionFactory.getCurrentSession().createQuery(clearStreets);
        query.executeUpdate();*/

        //creating accounts
        AccountBuilder ab = new AccountBuilder();

        ac1 = ab.build().withAccName("ACC_001").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).withRandomPhyAddress().getRes();
        ac2 = ab.build().withAccName("ACC_002").withAccType(Constants.AccountType.LEGAL).withBalance(50f).withRandomPhyAddress().getRes();
        id1 = dao.create(ac1);
        id2 = dao.create(ac2);
    }

    @Rollback(false) //just for experiment
    @Test
    public void a_storeFetchAndDeleteAccounts(){
        Account bean1 = dao.getById(id1);
        Account bean2 = dao.getById(id2);
        Account bean3 = dao.getById(0);

        assertTrue(bean3 == null);
        assertTrue(ac1.equals(bean1));
        assertTrue(ac2.equals(bean2));

        dao.delete(id1);
        Account res = dao.getById(id1);
        assertTrue(res == null);

        dao.setStatusDelete(id2);
        Account res2 = dao.getById(id2);
        assertTrue(res2.getStatus().equals(Status.DELETED));
    }

    @Rollback(true) //just for experiment
    @Test
    public void b_fetchListOfAccounts(){
        List<Account> resList = dao.getAccountList();

        /*assert    True(resList.size() == 2);*/
        assertTrue(resList.contains(ac1));
        assertTrue(resList.contains(ac2));

        }
        }
