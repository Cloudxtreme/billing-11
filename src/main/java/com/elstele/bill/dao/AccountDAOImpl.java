package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Account;
import com.elstele.bill.utils.Status;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDAOImpl extends CommonDAOImpl<Account> implements AccountDAO {
    @Override
    public List<Account> getAccountList(int limit, int offset) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select a from Account a where status <> 'DELETED'" +
                        "order by a.accountName");
        q.setFirstResult(offset).setMaxResults(limit);

        return (List<Account>)q.list();
    }

    @Override
    public List<Account> getAccountList() {
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        return (List<Account>)session.
                createCriteria(Account.class).add(Restrictions.ne("status", Status.DELETED))
                .list();
    }

    @Override
    public Integer getActiveAccountsCount() {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select count(* ) from Account a where status <> 'DELETED'");
        Long res = (Long)q.uniqueResult();
        return res.intValue();
    }


}
