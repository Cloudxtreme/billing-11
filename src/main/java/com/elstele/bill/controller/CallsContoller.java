package com.elstele.bill.controller;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.form.CallForm;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CallsContoller {
    @Autowired
    CallDataService callDataService;

    @RequestMapping(value = "/callsList", method = RequestMethod.GET)
    @ResponseBody
    public List<CallForm> getAccountList(HttpServletRequest request,
                                         @RequestParam(value = "rows") int rows,
                                         @RequestParam(value = "page") int page) {
        List<CallForm> result = callDataService.getCallsList(rows, page);
        return result;
    }

    @RequestMapping(value = "/searchCalls", method = RequestMethod.POST)
    @ResponseBody
    public List<CallForm> getAccountsListSearch(HttpServletRequest request,
                                                @RequestParam(value = "rows") int rows,
                                                @RequestParam(value = "page") int page,
                                                @RequestParam(value = "numberA") String numberA,
                                                @RequestParam(value = "numberB") String numberB,
                                                @RequestParam(value = "timeRange") String timeRange
    ) throws ParseException {
        Date startDate = null;
        Date endDate = null;
        if (timeRange.length() >= 16 && timeRange.length() < 36) {
            String x = timeRange.substring(0, 16);
            String y = timeRange.substring(19, 35);
            DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            startDate = df.parse(x);
            endDate = df.parse(y);
        }
        List<CallForm> result = callDataService.callsListSelectionBySearch(rows, page, numberA, numberB, startDate, endDate);
        return result;
    }

    @RequestMapping(value = "/callshome", method = RequestMethod.GET)
    public ModelAndView handleCallsHome(HttpSession session) {
        int totalPages = determineTotalPagesForOutput(10);
        ModelAndView mav = new ModelAndView("calls_list");
        mav.addObject("pageNum", 1);
        mav.addObject("pagesTotal", totalPages);
        return mav;
    }

    @RequestMapping(value = "/callsPages", method = RequestMethod.POST)
    @ResponseBody
    public String handleCallsHomeOnChange(HttpServletRequest request,
                                          @RequestParam(value = "pageResults") int pageResults) {
        int totalPages = determineTotalPagesForOutput(pageResults);
        return Integer.toString(totalPages);
    }

    private int determineTotalPagesForOutput(int pageCounts) {
        int accounts = callDataService.getCallsCount();
        if (accounts % pageCounts == 0)
            return accounts / pageCounts;
        else
            return (accounts / pageCounts) + 1;
    }
}
