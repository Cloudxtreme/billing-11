package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.LocalUser;
import org.hibernate.Filter;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalUserDAOImpl extends CommonDAOImpl<LocalUser> implements LocalUserDAO {

    @Override
    public LocalUser getLocalUserByNameAndPass(String name, String pass) {
        String hql = "from LocalUser where username =:name and password =:pass";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql)
                .setParameter("name", name)
                .setParameter("pass", pass);
        if (!query.list().isEmpty()){
            return (LocalUser)query.list().get(0);
        }
        return null;
    }

    @Override
    public List listLocalUser(){
        String hql = "from LocalUser user where user.status <> 'DELETED' or user.status is null ";
        Session session = getSessionFactory().getCurrentSession();
        Filter filter = session.enableFilter("showActive");
        filter.setParameter("exclude", new String("DELETED"));
        Query query = session.createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }

}
