package com.elstele.bill.controller;

import com.elstele.bill.form.ActivityForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class LocalUserController {

    @RequestMapping(value="/activity", method = RequestMethod.GET)
    public String Activity(HttpSession session, Map<String, Object> map)
    {
        map.put("activityForm", new ActivityForm());
        return "activity";
    }

    @RequestMapping(value="/activity", method = RequestMethod.POST)
    public String addActivity(HttpSession session, Map<String, Object> map)
    {
        map.put("activityForm", new ActivityForm());
        return "activity";
    }

}
