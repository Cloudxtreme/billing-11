package com.elstele.bill.datasrv;

import com.elstele.bill.dao.UserActivityDAO;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.UserPanelForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Service
public class ActivityDataServiceImpl implements ActivityDataService {

    @Autowired
    private UserActivityDAO userActivityDAO;

    @Override
    @Transactional
    public void saveActivity(ActivityForm form) {
        Activity activity = new Activity();
        activity.setName(form.getName());
        activity.setDescription(form.getDescription());
        if(form.isNew()){
            userActivityDAO.create(activity);
        }
        else{
            activity.setId(form.getId());
            userActivityDAO.update(activity);
        }
    }

    @Override
    @Transactional
    public void deleteActivity(Integer id) {
        userActivityDAO.delete(id);
    }

    @Override
    @Transactional
    public List<Activity> listActivity(){
        return userActivityDAO.listActivity();
    }

    @Override
    @Transactional
    public Activity findById(Integer id){
        return userActivityDAO.getById(id);
    }

}
