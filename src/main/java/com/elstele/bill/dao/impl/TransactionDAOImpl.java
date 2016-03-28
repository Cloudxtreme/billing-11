package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.TransactionDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.utils.Constants;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("transactionDAO")
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

    //TODO search by name as a string not a good for performance, need to change to ID
    @Override
    public List<Transaction> searchTransactionList(String account, Date dateStart, Date dateEnd) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        String orderBy = " ORDER BY transaction.date DESC";
        String addAccount = (!account.equals("")) ? " and lower(transaction.account.accountName) like :account " : "";
        String addDateStart = (dateStart != null) ? " and transaction.date > :dateStart " : "";
        String addDateEnd = (dateEnd != null) ? " and transaction.date < :dateEnd " : "";

        String hql = "from Transaction transaction where (transaction.status <> 'DELETED' or transaction.status is null) " +
                addAccount +
                addDateStart +
                addDateEnd +
                orderBy;

        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!addAccount.equals(""))
            query.setParameter("account", "%" + account.toLowerCase() + "%");
        if (!addDateStart.equals(""))
            query.setParameter("dateStart", dateStart);
        if (!addDateEnd.equals(""))
            query.setParameter("dateEnd", dateEnd);

        if (!query.list().isEmpty()) {
            transactionList = query.list();
        }
        return transactionList;
    }

    private Query getQuery(Integer accountId) {
        Query query;
        StringBuilder stringBuilder = new StringBuilder("from Transaction transaction where (transaction.status <> 'DELETED' or transaction.status is null) ");
        String orderBy = " ORDER BY transaction.date DESC";
        if (accountId > 0) {
            stringBuilder.append(" and transaction.account.id = :accountId ");
            stringBuilder.append(orderBy);
            query = getSessionFactory().getCurrentSession().createQuery(stringBuilder.toString())
                    .setParameter("accountId", accountId);
        } else {
            stringBuilder.append(orderBy);
            query = getSessionFactory().getCurrentSession().createQuery(stringBuilder.toString());
        }
        return query;
    }


    public void copyBalsnceToHistBalanceForAccount(Integer accountId, Float balance) {
        Query query;
        String sql = "INSERT INTO hist_balance (account, balance) VALUES (:accountId, :balance)";
        query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
                .setParameter("accountId", accountId)
                .setParameter("balance", balance);
        query.executeUpdate();
    }

    public Float getBalanceOnDateForAccount(Integer accountId, Date date){
        Query query;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        Date toDate = c.getTime();
        String sql = "SELECT balance FROM hist_balance WHERE hist_balance.account = :accountId " +
                " AND hist_balance.date < :toDate " +
                " AND hist_balance.date >= (:fromDate)";
        query = getSessionFactory().getCurrentSession().createSQLQuery(sql)
                .setParameter("accountId", accountId)
                .setParameter("fromDate", date)
                .setParameter("toDate", toDate);
        Float result = (Float)query.uniqueResult();
        if (result != null){
            return result;
        } else {
            return 0f;
        }
    }


    public List<Transaction> getDebetTransactionsByAccountForPeriod(Integer accountId, Date from, Date to) {
        Account account = new Account();
        account.setId(accountId);
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Transaction.class);
        criteria.add(Restrictions.ge("date", from));
        criteria.add(Restrictions.le("date", to));
        criteria.add(Restrictions.eq("account", account));
        criteria.add(Restrictions.eq("direction", Constants.TransactionDirection.DEBET));
        return (List<Transaction>)criteria.list();
    }

    public List<Transaction> getKreditTransactionsByAccountForPeriod(Integer accountId, Date from, Date to) {
        Account account = new Account();
        account.setId(accountId);
        Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Transaction.class);
        criteria.add(Restrictions.ge("date", from));
        criteria.add(Restrictions.le("date", to));
        criteria.add(Restrictions.eq("account", account));
        criteria.add(Restrictions.eq("direction", Constants.TransactionDirection.CREDIT));
        return (List<Transaction>)criteria.list();
    }

}
