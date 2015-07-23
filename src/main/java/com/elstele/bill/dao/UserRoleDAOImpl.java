package com.elstele.bill.dao;


import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleDAOImpl extends CommonDAOImpl<UserRole> implements UserRoleDAO {

    @Override
    public Integer insertUserRole(UserRole role) {
        return create(role);
    }

    @Override
    public List listUserRole(){
        String hql = "from UserRole";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }

}
