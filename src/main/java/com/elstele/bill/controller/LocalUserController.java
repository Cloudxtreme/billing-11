package com.elstele.bill.controller;

import com.elstele.bill.datasrv.LocalUserDataService;
import com.elstele.bill.datasrv.UserRoleDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.LocalUserForm;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.datasrv.ActivityDataService;
import com.elstele.bill.validator.ActivityValidator;
import com.elstele.bill.validator.LocalUserValidator;
import com.elstele.bill.validator.UserRoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class LocalUserController {

    @Autowired
    private ActivityDataService activityDataService;

    @Autowired
    private UserRoleDataService userRoleDataService;

    @Autowired
    private LocalUserDataService localUserDataService;

    @Autowired
    private UserRoleValidator userRoleValidator;

    @Autowired
    private ActivityValidator activityValidator;

    @Autowired
    private LocalUserValidator localUserValidator;

// ACTIVITY PART START
@RequestMapping(value = "/activity/{id}/delete", method = RequestMethod.GET)
public String activityDelete(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
    activityDataService.deleteActivity(id);
    map.put("activity", new Activity() );
    map.put("activityList", activityDataService.listActivity());
    map.put("successMessage","User Role was successfully deleted.");
    return "activity_list";
}

    // show User Role update form
    @RequestMapping(value = "/activity/{id}/update", method = RequestMethod.GET)
    public String activityUpdate(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        Activity activity = activityDataService.findById(id);
        UserRoleForm form = new UserRoleForm();
        form.setId(activity.getId());
        form.setName(activity.getName());
        form.setDescription(activity.getDescription());
        map.put("activityForm", form);
        map.put("activity", activity);
        return "activity_form";

    }

    @RequestMapping(value="/activity_form", method = RequestMethod.GET)
    public String activityAdd(HttpSession session, Map<String, Object> map)
    {
        map.put("activityForm", new ActivityForm());
        map.put("activity", new Activity() );
        return "activity_form";
    }

    @RequestMapping(value="/activity_form", method = RequestMethod.POST)
    public ModelAndView activityAdd(@ModelAttribute("activityForm") ActivityForm form, BindingResult result){
        activityValidator.validate(form, result);
        if (result.hasErrors()){
            ModelAndView mav = new ModelAndView("activity_form");
            mav.addObject("errorClass", "text-danger");
            return mav;
        }
        else{
            activityDataService.saveActivity(form);
            ModelAndView mav = new ModelAndView("activity_list");
            if(form.isNew()){
                mav.addObject("successMessage","Activity was successfully added.");
            }else{
                mav.addObject("successMessage","Activity was successfully updated.");
            }
            mav.addObject("activity", new Activity() );
            mav.addObject("activityList", activityDataService.listActivity());
            return mav;
        }

    }

    @RequestMapping(value="/activity_list", method = RequestMethod.GET)
    public String activityList(HttpSession session, Map<String, Object> map)
    {
        map.put("activity", new Activity() );
        map.put("activityList", activityDataService.listActivity());
        return "activity_list";
    }
// ACTIVITY PART END

// USER ROLE PART START
    // delete user role
    @RequestMapping(value = "/user_role/{id}/delete", method = RequestMethod.GET)
    public String userRoleDelete(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        userRoleDataService.deleteRole(id);
        map.put("userRole", new UserRole());
        map.put("userRoleList", userRoleDataService.listUserRole());
        map.put("activity", new Activity());
        map.put("activityList", activityDataService.listActivity());
        map.put("successMessage", "User Role was successfully deleted.");
        return "user_role_list";
    }

    // show User Role update form
    @RequestMapping(value = "/user_role/{id}/update", method = RequestMethod.GET)
    public String userRoleUpdate(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        UserRole userRole = userRoleDataService.findById(id);
        UserRoleForm form = new UserRoleForm();
        form.setId(userRole.getId());
        form.setName(userRole.getName());
        form.setDescription(userRole.getDescription());
        ArrayList<Integer> activityList = new ArrayList<Integer>();
        for (Activity activity : userRole.getActivities()) {
            activityList.add(activity.getId());
        }
        form.setActivityId(activityList);
        map.put("userRoleForm", form);
        map.put("userRole", userRole);
        map.put("activityList", activityDataService.listActivity());
        return "user_role_form";

    }

    @RequestMapping(value="/user_role_form", method = RequestMethod.GET)
    public String userRoleAdd(HttpSession session, Map<String, Object> map)
    {
        map.put("userRoleForm", new UserRoleForm());
        map.put("activity", new Activity() );
        map.put("activityList", activityDataService.listActivity());
        return "user_role_form";
    }

    @RequestMapping(value="/user_role_form", method = RequestMethod.POST)
    public ModelAndView userRoleAdd(@ModelAttribute("userRoleForm") UserRoleForm form, BindingResult result){
        userRoleValidator.validate(form, result);
        if (result.hasErrors()){
            ModelAndView mav = new ModelAndView("user_role_form");
            mav.addObject("activityList", activityDataService.listActivity());
            mav.addObject("errorClass", "text-danger");
            mav.addObject("userRoleList", userRoleDataService.listUserRole());
            return mav;
        }
        else{
            userRoleDataService.saveRole(form);
            ModelAndView mav = new ModelAndView("user_role_list");
            if(form.isNew()){
                mav.addObject("successMessage", "User Role was successfully added.");
            }else{
                mav.addObject("successMessage", "User Role was successfully updated.");
            }
            mav.addObject("userRole", new UserRole() );
            mav.addObject("userRoleList", userRoleDataService.listUserRole());
            mav.addObject("activity", new Activity() );
            mav.addObject("activityList", activityDataService.listActivity());
            return mav;
        }

    }

    @RequestMapping(value="/user_role_list", method = RequestMethod.GET)
    public String userRoleList(HttpSession session, Map<String, Object> map)
    {
        map.put("userRole", new UserRole() );
        map.put("userRoleList", userRoleDataService.listUserRole());
        map.put("activity", new Activity() );
        map.put("activityList", activityDataService.listActivity());
        return "user_role_list";
    }

// USER ROLE PART END

// LOCAL USER PART START
    // delete user role
    @RequestMapping(value = "/user/{id}/delete", method = RequestMethod.GET)
    public String userDelete(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        userRoleDataService.deleteRole(id);
        map.put("userRole", new UserRole());
        map.put("userRoleList", userRoleDataService.listUserRole());
        map.put("activity", new Activity());
        map.put("activityList", activityDataService.listActivity());
        map.put("successMessage", "User Role was successfully deleted.");
        return "user_role_list";
    }

    // show User Role update form
    @RequestMapping(value = "/user/{id}/update", method = RequestMethod.GET)
    public String userUpdate(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        UserRole userRole = userRoleDataService.findById(id);
        UserRoleForm form = new UserRoleForm();
        form.setId(userRole.getId());
        form.setName(userRole.getName());
        form.setDescription(userRole.getDescription());
        ArrayList<Integer> activityList = new ArrayList<Integer>();
        for (Activity activity : userRole.getActivities()) {
            activityList.add(activity.getId());
        }
        form.setActivityId(activityList);
        map.put("userRoleForm", form);
        map.put("userRole", userRole);
        map.put("activityList", activityDataService.listActivity());
        return "user_role_form";

    }

    @RequestMapping(value="/user_form", method = RequestMethod.GET)
    public String userAdd(HttpSession session, Map<String, Object> map)
    {
        map.put("localUserForm", new LocalUserForm());
        map.put("role", new UserRole() );
        map.put("roleList", userRoleDataService.listUserRole());
        return "user_form";
    }

    @RequestMapping(value="/user_form", method = RequestMethod.POST)
    public ModelAndView userAdd(@ModelAttribute("localUserForm") LocalUserForm form, BindingResult result){
        localUserValidator.validate(form, result);
        if (result.hasErrors()){
            ModelAndView mav = new ModelAndView("user_form");
            mav.addObject("roleList", userRoleDataService.listUserRole());
            mav.addObject("errorClass", "text-danger");
            mav.addObject("userList", localUserDataService.listLocalUser());
            return mav;
        }
        else{
//            localUserDataService.saveRole(form);
            ModelAndView mav = new ModelAndView("user_panel");
            if(form.isNew()){
                mav.addObject("successMessage", "User was successfully added.");
            }else{
                mav.addObject("successMessage", "User was successfully updated.");
            }
            mav.addObject("user", new LocalUser() );
            mav.addObject("userList", localUserDataService.listLocalUser());
            mav.addObject("userRole", new UserRole() );
            mav.addObject("userRoleList", userRoleDataService.listUserRole());
            return mav;
        }

    }

/*
    @RequestMapping(value="/user_panel", method = RequestMethod.GET)
    public String userRole(HttpSession session, Map<String, Object> map)
    {
        map.put("userRoleForm", new UserRoleForm());
        map.put("userRole", new UserRole() );
        map.put("userRoleList", userRoleDataService.listUserRole());
        map.put("activity", new Activity() );
        map.put("activityList", activityDataService.listActivity());
        return "user_panel";
    }
*/

    @RequestMapping(value="/user_panel", method = RequestMethod.GET)
    public String userList(HttpSession session, Map<String, Object> map)
    {
        map.put("user", new LocalUser() );
        map.put("userList", localUserDataService.listLocalUser());
/*
        map.put("userRole", new UserRole() );
        map.put("userRoleList", userRoleDataService.listUserRole());
*/
        return "user_panel";
    }

// LOCAL USER PART END
}
