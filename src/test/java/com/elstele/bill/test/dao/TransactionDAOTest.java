package com.elstele.bill.test.dao;

import com.elstele.bill.dao.TransactionDAO;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.test.builder.ObjectBuilder;
import com.elstele.bill.utils.Constants.Constants;
import com.elstele.bill.utils.Enums.Status;
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

    @Before
    public void setUp() {
        String hql = String.format("delete from Transaction");
        Query query = sessionFactory.getCurrentSession().createQuery(hql);
        query.executeUpdate();
    }

    @Test
    public void dbTransactionManupulation() {

        ObjectBuilder objectBuilder = new ObjectBuilder();
        Account ac1 = objectBuilder.createAccount(1,"ACC_001",20F,Constants.AccountType.PRIVATE);
        int accountId = accountDAO.create(ac1);

        Transaction trans1 = objectBuilder.createTransaction(1,Constants.TransactionDirection.DEBIT, 40.0F, Constants.TransactionSource.BANK, ac1);
        Transaction trans2 = objectBuilder.createTransaction(2,Constants.TransactionDirection.CREDIT, 100.0F, Constants.TransactionSource.HANDMADE, ac1);
        List<Transaction> transactionList = new ArrayList<Transaction>();
        transactionList.add(trans1);
        transactionList.add(trans2);

        int id1 = transactionDAO.create(trans1);
        int id2 = transactionDAO.create(trans2);

        Transaction bean1 = transactionDAO.getById(id1);
        Transaction bean2 = transactionDAO.getById(id2);
        Transaction bean3 = transactionDAO.getById(0);
        List<Transaction> transactionListFromDB = transactionDAO.getTransactionList(accountId);

        assertTrue(bean3 == null);
        assertTrue(trans1.equals(bean1));
        assertTrue(trans2.equals(bean2));
        assertTrue(transactionList.equals(transactionListFromDB));

        /*--- Update ---*/
        trans1.setDirection(Constants.TransactionDirection.CREDIT);
        transactionDAO.update(trans1);
        bean1 = transactionDAO.getById(id1);
        assertTrue(bean1.getDirection().equals(Constants.TransactionDirection.CREDIT));

        /*--- Delete ---*/
        transactionDAO.delete(id1);
        Transaction res = transactionDAO.getById(id1);
        assertTrue(res == null);

        transactionDAO.setStatusDelete(id2);
        res = transactionDAO.getById(id2);
        assertTrue(res.getStatus().equals(Status.DELETED));
    }
}