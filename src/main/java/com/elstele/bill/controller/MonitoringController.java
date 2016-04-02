package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.*;
import com.elstele.bill.domain.OnlineStatistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class MonitoringController {

    @Autowired
    private ServiceDataService serviceDataService;

    @Autowired
    private LogDataService logDataService;


    @RequestMapping(value="/getOnline", method = RequestMethod.GET)
    @ResponseBody
    public List<OnlineStatistic> getUsersOnline(){
        return serviceDataService.getUsersOnline();
    }

    @RequestMapping(value = "/statistic/statonline", method = RequestMethod.GET)
    public ModelAndView userOnlineHome(HttpSession session) {
        ModelAndView mav = new ModelAndView("statisticonline");
        return mav;
    }

    @RequestMapping(value="/getAuthLogLines", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getRadiusLogLastLines(){
        return logDataService.getAuthLogLastLines(100);
    }

}
