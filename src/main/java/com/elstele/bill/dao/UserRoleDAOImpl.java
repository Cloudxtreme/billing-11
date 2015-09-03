package com.elstele.bill.dao;


import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.UserRole;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.classic.Session;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleDAOImpl extends CommonDAOImpl<UserRole> implements UserRoleDAO {

    @Override
    public List listUserRole(){
        String hql = "from UserRole role where role.status <> 'DELETED' or role.status is null ";
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
