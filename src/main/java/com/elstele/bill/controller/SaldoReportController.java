package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.SaldoDataService;
import com.elstele.bill.form.SaldoResultForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;

@Controller
public class SaldoReportController {

    @Autowired
    private SaldoDataService saldoDataService;

    @RequestMapping(value = "/saldoReport", method = RequestMethod.GET)
    public ModelAndView showSaldoReport(){
        ModelAndView mav = new ModelAndView("saldoReport");

        Date from = calcFirstDayOfCurrentMonth();
        Date to = calcLastDayOfCurrentMonth();

        SaldoResultForm report = saldoDataService.generateSaldoResult(from, to);
        mav.addObject("report", report);
        return mav;
    }

    private Date calcLastDayOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        Integer lastDay = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, lastDay);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    private Date calcFirstDayOfCurrentMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }


}
