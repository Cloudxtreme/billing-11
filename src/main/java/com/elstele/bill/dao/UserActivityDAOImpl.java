package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Activity;
import org.springframework.stereotype.Service;

@Service
public class UserActivityDAOImpl extends CommonDAOImpl<Activity> implements UserActivityDAO {

    @Override
    public Integer insertUserActivity(Activity activity) {
        return create(activity);
    }

}
