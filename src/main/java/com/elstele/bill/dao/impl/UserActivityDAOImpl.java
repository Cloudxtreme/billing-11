package com.elstele.bill.dao.impl;


import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.UserActivityDAO;
import com.elstele.bill.domain.Activity;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserActivityDAOImpl extends CommonDAOImpl<Activity> implements UserActivityDAO {

    @Override
    public List listActivity(){
        List<Activity> activity = new ArrayList<Activity>();
        String hql = "from Activity activity where activity.status <> 'DELETED' or activity.status is null ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return activity;
    }
}
