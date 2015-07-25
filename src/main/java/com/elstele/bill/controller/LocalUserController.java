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
    private UserRoleValidator userRoleValidator;

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

    // delete user role
    @RequestMapping(value = "/user_role/{id}/delete", method = RequestMethod.GET)
    public String userRoleDelete(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        userRoleDataService.deleteRole(id);
        map.put("userRole", new UserRole() );
        map.put("userRoleList", userRoleDataService.listUserRole());
        map.put("activity", new Activity() );
        map.put("activityList", activityDataService.listActivity());
        map.put("successMessage","User Role was successfully deleted.");
        return "user_role_list";
    }


    // show User Role update form
    @RequestMapping(value = "/user_role/{id}/update", method = RequestMethod.GET)
    public String userRoleUpdate(@PathVariable("id") int id, HttpSession session, Map<String, Object> map) {
        UserRole userRole = userRoleDataService.findById(id);
        UserRoleForm form = new UserRoleForm();
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
            mav.addObject("userRole", new UserRole() );
            mav.addObject("userRoleList", userRoleDataService.listUserRole());
            mav.addObject("activity", new Activity() );
            mav.addObject("activityList", activityDataService.listActivity());
            mav.addObject("successMessage","User Role was successfully added.");
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
