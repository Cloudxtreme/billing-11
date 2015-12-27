package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.TransactionDAO;
import com.elstele.bill.domain.Transaction;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TransactionDAOImpl extends CommonDAOImpl<Transaction> implements TransactionDAO {
    @Override
    public List<Transaction> getTransactionList(Integer accountId) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        Query query = getQuery(accountId);
        if (!query.list().isEmpty()) {
            transactionList = query.list();
        }
        return transactionList;
    }

    @Override
    public List<Transaction> getTransactionList(Integer accountId, Integer displayLimit) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        Query query = getQuery(accountId);
        query = query.setMaxResults(displayLimit);
        if (!query.list().isEmpty()) {
            transactionList = query.list();
        }
        return transactionList;
    }

    @Override
    public List<Transaction> searchTransactionList(String account, Date dateStart, Date dateEnd){
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String orderBy = " ORDER BY transaction.date DESC";
        String addAccount = (!account.equals("")) ? " and transaction.account.accountName like :account " : "";
        String addDateStart = (dateStart != null) ? " and transaction.date > :dateStart " : "";
        String addDateEnd = (dateEnd != null) ? " and transaction.date < :dateEnd " : "";

        String hql = "from Transaction transaction where (transaction.status <> 'DELETED' or transaction.status is null) "+
                addAccount +
                addDateStart +
                addDateEnd +
                orderBy;

        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!addAccount.equals(""))
            query.setParameter("account","%"+account+"%");
        if (!addDateStart.equals(""))
            query.setParameter("dateStart",dateStart);
        if (!addDateEnd.equals(""))
            query.setParameter("dateEnd",dateEnd);

        if (!query.list().isEmpty()) {
            transactionList = query.list();
        }
        return transactionList;
    }

    private Query getQuery(Integer accountId){
        Query query;
        String orderBy = " ORDER BY transaction.date DESC";
        String hql = "from Transaction transaction where (transaction.status <> 'DELETED' or transaction.status is null) ";
        //TODO stringbuilder here
        if (accountId > 0) {
            hql += " and transaction.account.id = :accountId " + orderBy;
            query = getSessionFactory().getCurrentSession().createQuery(hql)
                    .setParameter("accountId", accountId);
        } else {
            query = getSessionFactory().getCurrentSession().createQuery( hql+orderBy );
        }
        return query;
    }
}
