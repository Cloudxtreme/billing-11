package com.elstele.bill.dao;


import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActivityDAOImpl extends CommonDAOImpl<Activity> implements UserActivityDAO {

    @Override
    public Integer insertUserActivity(Activity activity) {
        return create(activity);
    }

    @Override
    public void updateUserActivity(Activity activity) {
        update(activity);
    }

    @Override
    public void deleteUserActivity(Integer activityId) {
        delete(activityId);
    }

    @Override
    public List listActivity(){
        String hql = "from Activity";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }
}
