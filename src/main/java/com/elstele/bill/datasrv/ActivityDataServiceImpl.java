package com.elstele.bill.datasrv;

import com.elstele.bill.dao.UserActivityDAO;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.UserPanelForm;
import com.elstele.bill.form.UserRoleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Service
public class ActivityDataServiceImpl implements ActivityDataService {


    @Autowired
    private UserActivityDAO userActivityDAO;

    @Override
    public String isValid(String name, String description) {
        if (name != "" && description !=""){
            return "good";
        }
        return "bed";
    }

    @Override
    @Transactional
    public ModelAndView saveActivity(ActivityForm form, UserPanelForm returnForm) {
        String activityName = form.getName();
        String activityDescription = form.getDescription();
        if (isValid(activityName, activityDescription).equals("good")){
            ModelAndView mav = new ModelAndView("user_panel");
            Activity activity = new Activity();
            activity.setName(activityName);
            activity.setDescription(activityDescription);
            userActivityDAO.insertUserActivity(activity);
            mav.addObject("successMessage", "Activity <strong>'" + activityName + "'</strong> was added.");
            mav.addObject("activity", activity);
            mav.addObject("activityList", listActivity());
            return mav;
        }
        else {
            ModelAndView mav = new ModelAndView("activity");
            if (isValid(activityName, activityDescription).equals("bed") )
                mav.addObject("errorMessage", "<strong>Error!</strong> Some fields are empty. Fill in all the fields below. Please try again!");
            return mav;
        }
    }

    @Override
    @Transactional
    public ModelAndView editActivity(ActivityForm form, UserPanelForm returnForm) {
        String activityName = form.getName();
        String activityDescription = form.getDescription();
        if (isValid(activityName, activityDescription).equals("good")){
            ModelAndView mav = new ModelAndView("user_panel");
            Activity activity = new Activity();
            activity.setName(activityName);
            activity.setDescription(activityDescription);
            activity.setId(form.getActivityId());
            userActivityDAO.updateUserActivity(activity);
            mav.addObject("successMessage","Activity <strong>'"+activityName+"'</strong> was updated.");
            mav.addObject("activity", activity);
            mav.addObject("activityList", listActivity());
            return mav;
        }
        else {
            ModelAndView mav = new ModelAndView("activity");
            if(form.getActivityId()!= null){
                Activity activityEdit = getActivityFromId(form.getActivityId());
                form.setName(activityEdit.getName());
                form.setDescription(activityEdit.getDescription());
                form.setActivityId(form.getActivityId());
            }
            if (isValid(activityName, activityDescription).equals("bed") )
                mav.addObject("errorMessage", "<strong>Error!</strong> Some fields were empty. Please try again!");
            return mav;
        }
    }

    @Override
    @Transactional
    public ModelAndView deleteActivity(UserPanelForm form) {
        Activity activity = getActivityFromId(form.getActivityId());
        ModelAndView mav = new ModelAndView("user_panel");
        userActivityDAO.deleteUserActivity(activity.getId());
        mav.addObject("deleteMessage","Activity <strong>'"+activity.getName()+"'</strong> was deleted.");
        mav.addObject("activityList", listActivity());
        return mav;
    }

    @Override
    @Transactional
    public List<Activity> listActivity(){
        return userActivityDAO.listActivity();
    }

    @Override
    @Transactional
    public Activity getActivityFromId(Integer id){
        return userActivityDAO.getActivityById(id);
    }

}
