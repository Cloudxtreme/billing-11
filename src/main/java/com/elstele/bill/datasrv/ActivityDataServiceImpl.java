package com.elstele.bill.datasrv;

import com.elstele.bill.dao.UserActivityDAO;
import com.elstele.bill.domain.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ActivityDataServiceImpl implements ActivityDataService {


    @Autowired
    private UserActivityDAO userActivityDAO;

    @Override
    public boolean isCredentialValid(String name, String description) {
        if (name != "" && description !=""){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Integer saveActivity(Activity activity) {
        return userActivityDAO.insertUserActivity(activity);
    }

}
