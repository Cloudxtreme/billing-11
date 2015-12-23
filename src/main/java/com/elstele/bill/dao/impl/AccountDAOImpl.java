package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.utils.Enums.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AccountDAOImpl extends CommonDAOImpl<Account> implements AccountDAO {

    final static Logger log = LogManager.getLogger(AccountDAOImpl.class);

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

    public List<com.elstele.bill.domain.Service> getServiceByFIOAndName(String value) {
        try {
            Query query = getSessionFactory().getCurrentSession().
                    createQuery("From Service s where lower(s.account.fio) like '%" + value.toLowerCase() + "%' or s.account.accountName like '%" + value + "%'  ");
            log.info("Values selected successfully. Method searchAccounts ");
            return  (List<com.elstele.bill.domain.Service>)query.list();
        } catch (HibernateException e) {
            log.error(e.toString() + "Method getServiceByFIOAndName");
            return Collections.emptyList();
        }
    }

    public List<com.elstele.bill.domain.Service> getServiceByLogin(String value){
        Query query = getSessionFactory().getCurrentSession().
                createQuery("From Service s where lower(s.username) like '%" + value.toLowerCase() + "%' ");
        return (List<com.elstele.bill.domain.Service>)query.list();
    }

    public List<com.elstele.bill.domain.Service> getServiceByPhone(String value){
        Query query = getSessionFactory().getCurrentSession().
                createQuery("From Service s where s.phoneNumber like '%" + value + "%' ");
        return (List<com.elstele.bill.domain.Service>)query.list();
    }

}
