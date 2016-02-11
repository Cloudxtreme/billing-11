package com.elstele.bill.test.dao;

import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.TransactionDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.Builders.bean.AccountBuilder;
import com.elstele.bill.Builders.bean.TransactionBuilder;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
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
@ContextConfiguration("file:src/test/resources/test-servlet-context.xml")
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TransactionDAOTest {

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private TransactionDAO transactionDAO;
    @Autowired
    private AccountDAO accountDAO;

    private Transaction trans1;
    private Transaction trans2;

    @Before
    public void setUp() {
        String hql = String.format("delete from Transaction");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
        hql = String.format("delete from Service");
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
        hql = String.format("delete from Account");
        query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();

        AccountBuilder ab = new AccountBuilder();
        Account account = ab.build().withId(10).withAccName("ACC_001").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).withRandomPhyAddress().getRes();
        accountDAO.create(account);

        TransactionBuilder tb = new TransactionBuilder();
        trans1 = tb.build().randomTransaction().withAccount(account).getRes();
        trans2 = tb.build().randomTransaction().withAccount(account).getRes();
    }

    @After
    public void tearDown() {
        transactionDAO = null;
    }

    @Test
    public void a_createTransaction() {
        int id1 = transactionDAO.create(trans1);
        int id2 = transactionDAO.create(trans2);

        Transaction bean1 = transactionDAO.getById(id1);
        Transaction bean2 = transactionDAO.getById(id2);
        Transaction bean3 = transactionDAO.getById(0);

        assertTrue(bean3 == null);
        assertTrue(trans1.equals(bean1));
        assertTrue(trans2.equals(bean2));
    }

    @Test
    public void b_transactionList() {
        int id1 = transactionDAO.create(trans1);
        int id2 = transactionDAO.create(trans2);

        Transaction bean1 = transactionDAO.getById(id1);
        Transaction bean2 = transactionDAO.getById(id2);
        Transaction bean3 = transactionDAO.getById(0);

        List<Transaction> transactionList = new ArrayList<Transaction>();
        transactionList.add(trans1);
        transactionList.add(trans2);

        List<Transaction> transactionListFromDB = transactionDAO.getTransactionList(bean1.getAccount().getId());
        assertTrue(transactionList.equals(transactionListFromDB));
        assertTrue(transactionListFromDB.size() == 2);

        List<Transaction> transactionListFromDB1 = transactionDAO.getTransactionList(bean1.getAccount().getId(),1);
        assertTrue(transactionListFromDB1.size() == 1);

        transactionDAO.setStatusDelete(id1);
         List<Transaction> transactionListFromDB2 = transactionDAO.getTransactionList(bean1.getAccount().getId());
        assertTrue(transactionListFromDB2.size() == 1);
    }

    @Test
    public void c_updateTransaction() {
        int id1 = transactionDAO.create(trans1);
        int id2 = transactionDAO.create(trans2);

        trans1.setPrice(22222222222F);
        transactionDAO.update(trans1);
        Transaction bean1 = transactionDAO.getById(id1);
        assertTrue(bean1.getPrice().equals(22222222222F));
    }

    @Test
    public void d_deleteTransaction() {
        int id1 = transactionDAO.create(trans1);
        int id2 = transactionDAO.create(trans2);

        transactionDAO.delete(id1);
        Transaction res = transactionDAO.getById(id1);
        assertTrue(res == null);

        transactionDAO.setStatusDelete(id2);
        res = transactionDAO.getById(id2);
        assertTrue(res.getStatus().equals(Status.DELETED));
    }
}