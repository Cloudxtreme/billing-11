package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.TransactionDAO;
import com.elstele.bill.domain.Transaction;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
