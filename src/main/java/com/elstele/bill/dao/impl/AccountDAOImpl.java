package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Street;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDAOImpl extends CommonDAOImpl<Account> implements AccountDAO {


    public List<Account> getAccountList(int limit, int offset) {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query q = session.
                createQuery("select a from Account a where status <> 'DELETED'" +
                        "order by a.accountName");
        q.setFirstResult(offset).setMaxResults(limit);

        return (List<Account>) q.list();
    }

    public List<Account> getAccountList() {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        return (List<Account>) session.
                createCriteria(Account.class).add(Restrictions.ne("status", Status.DELETED))
                .addOrder(Order.asc("accountName"))
                .list();
    }

    public Integer getActiveAccountsCount() {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Account a where status <> 'DELETED'");
        Long res = (Long) q.uniqueResult();
        return res.intValue();
    }

    public List<Account> searchAccounts(String value) {
        Query query = getSessionFactory().getCurrentSession().
                createQuery("from Account a where lower(a.fio) like '%" + value.toLowerCase() + "%' or a.accountName like '%" + value + "%'");
        return (List<Account>) query.list();
    }

}
