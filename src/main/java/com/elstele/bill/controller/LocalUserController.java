package com.elstele.bill.controller;

import com.elstele.bill.datasrv.UserRoleDataService;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.datasrv.ActivityDataService;
import com.elstele.bill.form.UserPanelForm;
import com.elstele.bill.validator.UserRoleValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping(value="/activity_add", method = RequestMethod.POST)
    public ModelAndView activityAdd(@ModelAttribute("activityForm") ActivityForm form, @ModelAttribute("userPanelForm") UserPanelForm returnForm){
        return activityDataService.saveActivity(form, returnForm);
    }

    @RequestMapping(value="/activity_edit", method = RequestMethod.POST)
    public ModelAndView activityEdit(@ModelAttribute("activityForm") ActivityForm form, @ModelAttribute("userPanelForm") UserPanelForm returnForm){
        return activityDataService.editActivity(form, returnForm);
    }

    @RequestMapping(value="/activity_delete", method = RequestMethod.POST)
    public ModelAndView activityDelete(@ModelAttribute("userPanelForm") UserPanelForm form){
        return activityDataService.deleteActivity(form);
    }

    @RequestMapping(value="/activity", method = RequestMethod.GET)
    public String addActivity(HttpSession session, Map<String, Object> map)
    {
        return "activity";
    }

    @RequestMapping(value="/user_panel", method = RequestMethod.POST)
    public String userPanelCall(HttpSession session, Map<String, Object> map)
    {
        return "user_panel";
    }

/*
    @RequestMapping(value="/user_panel", method = RequestMethod.GET)
    public String userPanel(HttpSession session, Map<String, Object> map)
    {
        map.put("userPanelForm", new UserPanelForm());
        map.put("activity", new Activity() );
        map.put("activityList", activityDataService.listActivity());
        return "user_panel";
    }
*/

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
        UserRoleValidator roleValidator = new UserRoleValidator();
        roleValidator.validate(form, result);
        if (result.hasErrors()){
            ModelAndView mav = new ModelAndView("user_role_form");
            mav.addObject("activityList", activityDataService.listActivity());
            mav.addObject("errorClass", "text-danger");
            mav.addObject("userRoleList", userRoleDataService.listUserRole());
            return mav;
        }
        else{
            ModelAndView mav = new ModelAndView("user_role_list");
            mav.addObject("userRole", new UserRole() );
            mav.addObject("userRoleList", userRoleDataService.listUserRole());
            mav.addObject("activity", new Activity() );
            mav.addObject("activityList", activityDataService.listActivity());
            mav.addObject("successMessage","User Role was successfully added.");
            userRoleDataService.saveRole(form);
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

}
