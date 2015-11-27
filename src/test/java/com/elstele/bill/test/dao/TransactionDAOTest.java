package com.elstele.bill.test.dao;

import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.dao.TransactionDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Status;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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

import java.sql.Timestamp;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionDAOTest {

    @Autowired
    private TransactionDAO transactionDAO;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private AccountDAO accountDAO;

    @Before
    public void setUp(){
        String hql = String.format("delete from Transaction");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

    @Test
    public void createUpdateAndDeleteTransaction(){
        java.util.Date date = new java.util.Date();

        Account ac1 = new Account();
        ac1.setAccountName("ACC_001");
        ac1.setCurrentBalance(20F);
        ac1.setAccountType(Constants.AccountType.PRIVATE);
        ac1.setStatus(Status.ACTIVE);
        accountDAO.create(ac1);

        Transaction trans1 = new Transaction();
        trans1.setDate(new Timestamp(date.getTime()));
        trans1.setDirection(Constants.TransactionDirection.DEBIT);
        trans1.setPrice(40.0F);
        trans1.setSource(Constants.TransactionSource.BANK);
        trans1.setAccount(ac1);
        trans1.setStatus(Status.ACTIVE);

        Transaction trans2 = new Transaction();
        trans2.setDate(new Timestamp(date.getTime()));
        trans2.setDirection(Constants.TransactionDirection.CREDIT);
        trans2.setPrice(100.0F);
        trans2.setSource(Constants.TransactionSource.HANDMADE);
        trans2.setAccount(ac1);
        trans2.setStatus(Status.ACTIVE);

        int id1 = transactionDAO.create(trans1);
        int id2 = transactionDAO.create(trans2);

        Transaction bean1 = transactionDAO.getById(id1);
        Transaction bean2 = transactionDAO.getById(id2);
        Transaction bean3 = transactionDAO.getById(0);

        assertTrue(bean3 == null);
        assertTrue(trans1.equals(bean1));
        assertTrue(trans2.equals(bean2));


        trans1.setDirection(Constants.TransactionDirection.CREDIT);
        transactionDAO.update(trans1);
        bean1 = transactionDAO.getById(id1);
        assertTrue(bean1.getDirection().equals(Constants.TransactionDirection.CREDIT));


        transactionDAO.delete(id1);
        Transaction res = transactionDAO.getById(id1);
        assertTrue(res == null);

        transactionDAO.setStatusDelete(id2);
        res = transactionDAO.getById(id2);
        assertTrue(res.getStatus().equals(Status.DELETED));
    }

}
