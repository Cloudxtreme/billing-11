package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.LocalUserDataService;
import com.elstele.bill.datasrv.interfaces.UserRoleDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.LocalUserForm;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.datasrv.interfaces.ActivityDataService;
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
    map.put("activityList", activityDataService.listActivity());
    map.put("successMessage","Activity was successfully deleted.");
    return "activity_list";
}

    // show Activity update form
    @RequestMapping(value = "/activity/{id}/update", method = RequestMethod.GET)
    public String activityUpdate(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        ActivityForm form = activityDataService.getActivityFormById(id);
        map.put("activityForm", form);
        return "activity_form";

    }

    @RequestMapping(value = "/addactivity", method = RequestMethod.GET)
    public String activityAdd(HttpSession session, Map<String, Object> map) {
        map.put("activityForm", new ActivityForm());
        return "activity_form";
    }

    @RequestMapping(value = "/addactivity", method = RequestMethod.POST)
    public ModelAndView activityAdd(@ModelAttribute("activityForm") ActivityForm form, BindingResult result) {
        activityValidator.validate(form, result);
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("activity_form");
            mav.addObject("errorClass", "text-danger");
            return mav;
        } else {
            String message = activityDataService.saveActivity(form);
            ModelAndView mav = new ModelAndView("activity_list");
            mav.addObject("successMessage", message);
            mav.addObject("activityList", activityDataService.listActivity());
            return mav;
        }

    }

    @RequestMapping(value="/activitylist", method = RequestMethod.GET)
    public String activityList(HttpSession session, Map<String, Object> map)
    {
        map.put("activityList", activityDataService.listActivity());
        return "activity_list";
    }
// ACTIVITY PART END

    // USER ROLE PART START
    // delete user role
    @RequestMapping(value = "/user_role/{id}/delete", method = RequestMethod.GET)
    public String userRoleDelete(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        userRoleDataService.deleteRole(id);
        map.put("userRoleList", userRoleDataService.listUserRole());
        map.put("successMessage", "User Role was successfully deleted.");
        return "user_role_list";
    }

    // show User Role update form
    @RequestMapping(value = "/user_role/{id}/update", method = RequestMethod.GET)
    public String userRoleUpdate(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        UserRoleForm form = userRoleDataService.getUserRoleFormById(id);
        map.put("userRoleForm", form);
        map.put("activityList", activityDataService.listActivity());
        return "userRoleFormModel";

    }

    @RequestMapping(value = "/user_role_form", method = RequestMethod.GET)
    public String userRoleAdd(HttpSession session, Map<String, Object> map) {
        map.put("userRoleForm", new UserRoleForm());
       map.put("activityList", activityDataService.listActivity());
        return "user_role_form";
    }

    @RequestMapping(value = "/user_role_form", method = RequestMethod.POST)
    public ModelAndView userRoleAdd(@ModelAttribute("userRoleForm") UserRoleForm form, BindingResult result) {
        userRoleValidator.validate(form, result);
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("userRoleFormModel");
            mav.addObject("activityList", activityDataService.listActivity());
            mav.addObject("errorClass", "text-danger");
            mav.addObject("userRoleList", userRoleDataService.listUserRole());
            return mav;
        } else {
            String message = userRoleDataService.saveRole(form);
            ModelAndView mav = new ModelAndView("user_role_list");
            mav.addObject("successMessage",message);
            mav.addObject("userRoleList", userRoleDataService.listUserRole());
            return mav;
        }
    }

    @RequestMapping(value="/userrolelist", method = RequestMethod.GET)
    public String userRoleList(HttpSession session, Map<String, Object> map)
    {
        map.put("userRoleList", userRoleDataService.listUserRole());
        return "user_role_list";
    }

// USER ROLE PART END

    // LOCAL USER PART START
    // delete user role
    @RequestMapping(value = "/user/{id}/delete", method = RequestMethod.GET)
    public String userDelete(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        localUserDataService.deleteUser(id);
        map.put("user", new LocalUser());
        map.put("userList", localUserDataService.listLocalUser());
        map.put("userRole", new UserRole());
        map.put("roleList", userRoleDataService.listUserRole());
        map.put("successMessage", "User was successfully deleted.");
        return "user_panel";
    }

    // show User Role update form
    @RequestMapping(value = "/user/{id}/update", method = RequestMethod.GET)
    public String userUpdate(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        LocalUserForm form = localUserDataService.getLocalUserFormById(id);
        map.put("localUserForm", form);
        map.put("roleList", userRoleDataService.listUserRole());
        return "user_form";

    }

    @RequestMapping(value = "/userform", method = RequestMethod.GET)
    public String userAdd(HttpSession session, Map<String, Object> map) {
        map.put("localUserForm", new LocalUserForm());
        map.put("role", new UserRole());
        map.put("roleList", userRoleDataService.listUserRole());
        return "user_form";
    }

    @RequestMapping(value = "/userform", method = RequestMethod.POST)
    public ModelAndView userAdd(@ModelAttribute("localUserForm") LocalUserForm form, BindingResult result) {
        localUserValidator.validate(form, result);
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("user_form");
            mav.addObject("roleList", userRoleDataService.listUserRole());
            mav.addObject("errorClass", "text-danger");
            mav.addObject("userList", localUserDataService.listLocalUser());
            return mav;
        } else {
            String message = localUserDataService.saveUser(form);
            ModelAndView mav = new ModelAndView("user_panel");
            mav.addObject("successMessage", message);
            mav.addObject("user", new LocalUser());
            mav.addObject("userList", localUserDataService.listLocalUser());
            mav.addObject("userRole", new UserRole());
            mav.addObject("roleList", userRoleDataService.listUserRole());
            return mav;
        }

    }

    @RequestMapping(value = "/userpanel", method = RequestMethod.GET)
    public String userList(HttpSession session, Map<String, Object> map) {
        map.put("user", new LocalUser());
        map.put("userList", localUserDataService.listLocalUser());
        map.put("userRole", new UserRole());
        map.put("roleList", userRoleDataService.listUserRole());
        return "user_panel";
    }

// LOCAL USER PART END
}
