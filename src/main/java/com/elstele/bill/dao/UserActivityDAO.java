package com.elstele.bill.dao;


import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Activity;

public interface UserActivityDAO extends CommonDAO <Activity> {
    public Integer insertUserActivity(Activity activity);
}
