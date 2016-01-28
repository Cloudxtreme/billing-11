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
import java.text.ParseException;
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
        CallsRequestParamTO paramTO = new CallsRequestParamTO();
        paramTO.setCallNumberA(numberA);
        paramTO.setCallNumberB(numberB);
        paramTO.setPage(page);
        paramTO.setRows(rows);
        paramTO.setSelectedTime(timeRange);
        return callDataService.getCallsList(paramTO);
    }

    @RequestMapping(value = "/callshome", method = RequestMethod.GET)
    public ModelAndView handleCallsHome(HttpSession session) {
        CallsRequestParamTO callsRequestParamTO = new CallsRequestParamTO();
        callsRequestParamTO.setPageResults(10);
        int totalPages = callDataService.determineTotalPagesForOutput(callsRequestParamTO);
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
        int totalPages = callDataService.determineTotalPagesForOutput(callsRequestParamTO);
        return Integer.toString(totalPages);
    }

}
