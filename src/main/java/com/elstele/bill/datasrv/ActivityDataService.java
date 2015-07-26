package com.elstele.bill.datasrv;

import com.elstele.bill.domain.Activity;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.UserPanelForm;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface ActivityDataService {

    public String isValid(String name, String description);
    public ModelAndView saveActivity(ActivityForm form, UserPanelForm returnForm);
    public ModelAndView editActivity(ActivityForm form, UserPanelForm returnForm);
    public ModelAndView deleteActivity(UserPanelForm form);
    public List<Activity> listActivity();
    public Activity getActivityFromId(Integer id);

}
