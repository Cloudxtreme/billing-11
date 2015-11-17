package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.Activity;
import com.elstele.bill.form.ActivityForm;

import java.util.List;

public interface ActivityDataService {

    public String saveActivity(ActivityForm form);
    public void deleteActivity(Integer id);
    public List<Activity> listActivity();
    public Activity findById(Integer id);
    public ActivityForm getActivityFormById(Integer id);

}
