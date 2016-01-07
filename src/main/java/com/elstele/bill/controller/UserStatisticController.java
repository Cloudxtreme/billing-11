package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.UserStatisticDataService;
import com.elstele.bill.domain.Radacct;
import com.elstele.bill.utils.CalendarUtils;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.CustomizeCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.*;

@Controller
public class UserStatisticController {
    @Autowired
    private UserStatisticDataService userStatisticDataService;

    @RequestMapping(value="/statistic", method = RequestMethod.GET)
    public ModelAndView serviceTypeList(@RequestParam(value = "login") int login)  throws ParseException
    {
        ModelAndView mav = new ModelAndView("userStatistic");
        CalendarUtils calendarUtils = new CalendarUtils();

        mav.addObject("startDate", calendarUtils.getYearMonthAfterDecriment(Constants.STATISTIC_MONTHS_DISPLAY_DEFAULT));
        mav.addObject("endDate", calendarUtils.getCurrentYear() + "-" + calendarUtils.getCurrentMonth());
        mav.addObject("login", login);
        return mav;
    }

    @RequestMapping(value = "getUserStatisticForPeriod", method = RequestMethod.GET)
    @ResponseBody
    public List<CustomizeCalendar> getUserStatisticForPeriod(HttpServletRequest request,
                                             @RequestParam(value = "login") int login,
                                             @RequestParam(value = "startDate") String startDate,
                                             @RequestParam(value = "endDate") String endDate) throws ParseException{
        List<CustomizeCalendar> result = userStatisticDataService.getCustomizeCalendar(login, startDate, endDate);
        return result;
    }

    @RequestMapping(value = "getUserDailyStatistic", method = RequestMethod.GET)
    @ResponseBody
    public List<Radacct> getUserDailyStatistic(HttpServletRequest request,
                                                             @RequestParam(value = "login") int login,
                                                             @RequestParam(value = "date") String date){
        List<Radacct> result = userStatisticDataService.getDailyStatistic(login, date);
        return result;
    }

}

