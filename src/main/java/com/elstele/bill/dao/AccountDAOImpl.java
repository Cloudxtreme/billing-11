package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Account;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountDAOImpl extends CommonDAOImpl<Account> implements AccountDAO {
    @Override
    public List<Account> getAccountsList(int limit, int offset) {
        Query q = getSessionFactory().getCurrentSession().
                createQuery("select a from Account a order by a.accountName");
        q.setFirstResult(0).setMaxResults(100);

        return (List<Account>)q.list();
    }

    @Override
    public List<Account> getAccountList() {
        return (List<Account>)getSessionFactory().getCurrentSession().
                createCriteria(Account.class).list();
    }
}
