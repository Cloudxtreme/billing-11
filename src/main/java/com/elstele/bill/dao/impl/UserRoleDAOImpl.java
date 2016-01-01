package com.elstele.bill.dao.impl;


import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.UserRoleDAO;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.UserRole;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserRoleDAOImpl extends CommonDAOImpl<UserRole> implements UserRoleDAO {

    @Override
    public List listUserRole(){
        String hql = "from UserRole role where role.status <> 'DELETED' or role.status is null ";
        Session session = getSessionFactory().getCurrentSession();
        setFilter(session, "showActive");
        Query query = session.createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return Collections.emptyList();
    }

    @Override
    public UserRole getByName(String name) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from UserRole role where role.name =:name ");
        query.setParameter("name", name);
        return (UserRole)query.uniqueResult();
    }

}
