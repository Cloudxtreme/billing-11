package com.elstele.bill.dao.interfaces;


import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;

import java.util.List;

public interface UserActivityDAO extends CommonDAO <Activity> {
    public List<Activity> listActivity();
    public Integer insertUserActivity(Activity activity);
    public void updateUserActivity(Activity activity);
    public void deleteUserActivity(Integer activityId);
}
