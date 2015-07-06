package com.elstele.bill.controller;

import com.elstele.bill.form.ActivityForm;
import com.elstele.bill.domain.Activity;
import com.elstele.bill.datasrv.ActivityDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LocalUserController {

    @Autowired
    private ActivityDataService activityDataService;

    @RequestMapping(value="/activity", method = RequestMethod.POST)
    public ModelAndView activityCall(@ModelAttribute("activityForm") ActivityForm activityForm, HttpSession session,
                                  HttpServletRequest request){
        String activityName = activityForm.getName();
        String activityDescription = activityForm.getDescription();
        ModelAndView mav = new ModelAndView("activity");
        if (activityDataService.isCredentialValid(activityName, activityDescription)){
            Activity activity = new Activity();
            activity.setName(activityName);
            activity.setDescription(activityDescription);
            activityDataService.saveActivity(activity);
            return mav;
        }
        ModelAndView activity = new ModelAndView("activity");
        activity.addObject("errorMessage", "Fill all the fields below. Please try again!");
        return activity;
    }

    @RequestMapping(value="/activity", method = RequestMethod.GET)
    public String addActivity(HttpSession session, Map<String, Object> map)
    {
        map.put("activityForm", new ActivityForm());
        return "activity";
    }

}
