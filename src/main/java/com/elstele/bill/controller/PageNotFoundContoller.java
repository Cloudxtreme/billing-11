package com.elstele.bill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageNotFoundContoller {

    @RequestMapping(value="/404page", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView redirectToThe404(){
        ModelAndView mav = new ModelAndView("404");
        return mav;
    }
}
