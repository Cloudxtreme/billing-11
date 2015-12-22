package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.reportCreators.CallsRequestParamTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class CallsContoller {
    @Autowired
    CallDataService callDataService;

    @RequestMapping(value = "/callsList", method = RequestMethod.GET)
    @ResponseBody
    public List<CallForm> getAccountsListSearch(HttpServletRequest request,
                                                @RequestParam(value = "rows") int rows,
                                                @RequestParam(value = "page") int page,
                                                @RequestParam(value = "numberA") String numberA,
                                                @RequestParam(value = "numberB") String numberB,
                                                @RequestParam(value = "timeRange") String timeRange
    ) throws ParseException {
        List<CallForm> result = new ArrayList<>();
        Date startDate = null;
        Date endDate = null;
        if (numberA.isEmpty() && numberB.isEmpty() && timeRange.isEmpty()) {
             result = callDataService.getCallsList(rows, page);
        } else {
            if (timeRange.length() >= 16 && timeRange.length() < 36) {
                String startDateTemp = timeRange.substring(0, 16);
                String endDateTemp = timeRange.substring(19, 35);
                DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                startDate = df.parse(startDateTemp);
                endDate = df.parse(endDateTemp);
            }
            result = callDataService.callsListSelectionBySearch(rows, page, numberA, numberB, startDate, endDate);
        }

        return result;
    }

    @RequestMapping(value = "/callshome", method = RequestMethod.GET)
    public ModelAndView handleCallsHome(HttpSession session) {
        CallsRequestParamTO callsRequestParamTO = new CallsRequestParamTO();
        callsRequestParamTO.setPageResults(10);
        int totalPages = determineTotalPagesForOutput(callsRequestParamTO);
        ModelAndView mav = new ModelAndView("calls_list");
        mav.addObject("pageNum", 1);
        mav.addObject("pagesTotal", totalPages);
        return mav;
    }

    @RequestMapping(value = "/callsPages", method = RequestMethod.POST)
    @ResponseBody
    public String handleCallsHomeOnChange(HttpServletRequest request,
                                          @RequestParam(value = "pageResults") int pageResults,
                                          @RequestParam(value = "numberA") String numberA,
                                          @RequestParam(value = "numberB") String numberB,
                                          @RequestParam(value = "timeRange") String timeRange) throws ParseException {
        CallsRequestParamTO callsRequestParamTO = new CallsRequestParamTO();
        callsRequestParamTO.setCallNumberA(numberA);
        callsRequestParamTO.setCallNumberB(numberB);
        callsRequestParamTO.setEndDate(timeRange);
        callsRequestParamTO.setStartDate(timeRange);
        callsRequestParamTO.setPageResults(pageResults);
        int totalPages = determineTotalPagesForOutput(callsRequestParamTO);
        return Integer.toString(totalPages);
    }

    private int determineTotalPagesForOutput(CallsRequestParamTO callsRequestParamTO) {
        int callsCount = 0;
        if (callsRequestParamTO.getStartDate() == null && callsRequestParamTO.getEndDate() == null &&
                (callsRequestParamTO.getCallNumberA() == null || callsRequestParamTO.getCallNumberA().isEmpty())
                && (callsRequestParamTO.getCallNumberB() == null || callsRequestParamTO.getCallNumberB().isEmpty())) {
            callsCount = callDataService.getCallsCount();
        } else {
            callsCount = callDataService.getCallsCountWithSearchValues(callsRequestParamTO);
        }
        int containedCount = callsRequestParamTO.getPageResults();
        if (callsCount % containedCount == 0)
            return callsCount / containedCount;
        else
            return (callsCount / containedCount) + 1;
    }
}
