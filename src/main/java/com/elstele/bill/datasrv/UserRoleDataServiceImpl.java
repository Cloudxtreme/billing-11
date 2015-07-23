package com.elstele.bill.datasrv;

import com.elstele.bill.dao.UserActivityDAO;
import com.elstele.bill.dao.UserRoleDAO;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Service
public class UserRoleDataServiceImpl implements UserRoleDataService {


    @Autowired
    private UserRoleDAO userRoleDAO;

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
    public void saveRole(UserRoleForm form){
        UserRole role = new UserRole();
        role.setName(form.getName());
        role.setDescription(form.getDescription());
        for (int activityId : form.getActivityId()) {
            role.addActivity(userActivityDAO.getActivityById(activityId));
        }
        userRoleDAO.insertUserRole(role);
    }

    @Override
    @Transactional
    public ModelAndView editRole(UserRoleForm form){
        ModelAndView mav = new ModelAndView("user_panel");
        return mav;
    }

    @Override
    @Transactional
    public ModelAndView deleteRole(UserRoleForm form){
        ModelAndView mav = new ModelAndView("user_panel");
        Activity activity = new Activity();
        mav.addObject("activity", activity);
        mav.addObject("activityList", userActivityDAO.listActivity());
        return mav;
    }

    @Override
    @Transactional
    public List<UserRole> listUserRole(){
        return userRoleDAO.listUserRole();
    }

}
