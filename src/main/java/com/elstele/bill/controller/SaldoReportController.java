package com.elstele.bill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SaldoReportController {

    @RequestMapping(value = "/saldoReport", method = RequestMethod.GET)
    public ModelAndView showSaldoReport(){
        ModelAndView mav = new ModelAndView("saldoReport");

        return mav;
    }


}
