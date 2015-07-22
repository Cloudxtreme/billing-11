package com.elstele.bill.test.dao;

import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Status;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.junit.Assert;
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

import java.util.List;

import static org.junit.Assert.*;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountDAOTest {

    @Autowired
    private AccountDAO dao;
    @Autowired
    private SessionFactory sessionFactory;


    @Before
    public void setUp(){
        String hql = String.format("delete from Account");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

    @Test
    public void storeFetchAndDeleteAccounts(){
        Account ac1 = new Account();
        ac1.setAccountName("ACC_001");
        ac1.setCurrentBalance(20F);
        ac1.setAccountType(Constants.AccountType.PRIVATE);
        ac1.setStatus(Status.ACTIVE);

        Account ac2 = new Account();
        ac2.setAccountName("ACC_002");
        ac2.setCurrentBalance(50.0F);
        ac2.setAccountType(Constants.AccountType.LEGAL);
        ac2.setStatus(Status.ACTIVE);

        int id1 = dao.create(ac1);
        int id2 = dao.create(ac2);

        Account bean1 = dao.getById(id1);
        Account bean2 = dao.getById(id2);
        Account bean3 = dao.getById(0);

        assertTrue(bean3 == null);
        assertTrue(ac1.equals(bean1));
        assertTrue(ac2.equals(bean2));

        dao.delete(id1);
        Account res = dao.getById(id1);
        assertTrue(res == null);

    }

    @Test
    public void fetchListOfAccounts(){
        Account ac1 = new Account();
        ac1.setAccountName("ACC_001");
        ac1.setCurrentBalance(20F);
        ac1.setAccountType(Constants.AccountType.PRIVATE);
        ac1.setStatus(Status.ACTIVE);

        Account ac2 = new Account();
        ac2.setAccountName("ACC_002");
        ac2.setCurrentBalance(50.0F);
        ac2.setAccountType(Constants.AccountType.LEGAL);
        ac2.setStatus(Status.ACTIVE);

        int id1 = dao.create(ac1);
        int id2 = dao.create(ac2);

        List<Account> resList = dao.getAccountList();

        assertTrue(resList.size() == 2);
        assertTrue(resList.contains(ac1));
        assertTrue(resList.contains(ac2));

    }
}
