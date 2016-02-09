package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.elstele.bill.utils.Enums.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDAOImpl extends CommonDAOImpl<Account> implements AccountDAO {
    @Autowired
    AuditedObjectDAO auditedObjectDAO;

    final static Logger LOGGER = LogManager.getLogger(AccountDAOImpl.class);

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

    /**
     * Mehod need to be used in cases when we are worry about concurrent changes of Account, especially during balance changes
     * */
    public Account getAccountForUpgradeById(Integer id) {
        Session session = getSessionFactory().getCurrentSession();
        return (Account)session.get(Account.class, id, LockOptions.UPGRADE);
    }

    public Integer getActiveAccountsCount() {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Account a where status <> 'DELETED'");
        Long res = (Long) q.uniqueResult();
        return res.intValue();
    }

    public List<Account> getAccountByFIOAndName(String value) {
        Query query = getSessionFactory().getCurrentSession().
                createQuery("From Account a where lower(a.fio) like '%" + value.toLowerCase() + "%' or a.accountName like '%" + value + "%'  ");
        LOGGER.info("Values selected successfully. Method searchAccounts ");
        return (List<Account>) query.list();

    }


    public Integer create(Account account, String changerName){
        Integer id = (Integer) getSessionFactory().getCurrentSession().save(account);
        auditedObjectDAO.create(account, ObjectOperationType.CREATE, changerName);
        return id;
    }

    public void update(Account account, String changerName) {
        getSessionFactory().getCurrentSession().saveOrUpdate(account);
        getSessionFactory().getCurrentSession().flush();
        auditedObjectDAO.create(account, ObjectOperationType.UPDATE, changerName);
    }

}
