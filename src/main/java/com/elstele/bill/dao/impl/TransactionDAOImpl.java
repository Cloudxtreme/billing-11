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
        String hql = "from Transaction transaction where (transaction.status <> 'DELETED' or transaction.status is null) ";
        Query query;
        if(accountId>0){
            hql += " and transaction.account.id = :accountId ";
            query = getSessionFactory().getCurrentSession().createQuery(hql)
                    .setParameter("accountId", accountId);
        }
        else{
            query = getSessionFactory().getCurrentSession().createQuery(hql);
        }
        if (!query.list().isEmpty()){
            transactionList = query.list();
        }
        return transactionList;
    }
}
