package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.ActivityAssembler;
import com.elstele.bill.dao.UserActivityDAO;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ActivityDataServiceImpl implements ActivityDataService {

    @Autowired
    private UserActivityDAO userActivityDAO;

    @Override
    @Transactional
    public String saveActivity(ActivityForm form) {
        ActivityAssembler assembler = new ActivityAssembler();
        Activity activity = assembler.fromFormToBean(form);
        String message = "Activity was successfully ";
        if(form.isNew()){
            userActivityDAO.create(activity);
            message += "added.";
        }
        else{
            userActivityDAO.update(activity);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public void deleteActivity(Integer id) {
        userActivityDAO.setStatusDelete(id);
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

    @Override
    @Transactional
    public ActivityForm getActivityFormById(Integer id){
        ActivityAssembler assembler = new ActivityAssembler();
        ActivityForm result = null;
        Activity bean = userActivityDAO.getById(id);
        if (bean != null){
            ActivityForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;

    }


}
