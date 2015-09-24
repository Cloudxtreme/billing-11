package com.elstele.bill.controller;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.form.CallForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CallsContoller {
    @Autowired
    CallDataService callDataService;

    @RequestMapping(value="/callsList", method = RequestMethod.GET)
    @ResponseBody
    public List<CallForm> getAccountList(HttpServletRequest request,
                                         @RequestParam(value = "rows") int rows,
                                         @RequestParam(value = "page") int page){
        List<CallForm> result = callDataService.getCallsList(rows, page);
        return result;
    }

    @RequestMapping(value="/callshome", method = RequestMethod.GET)
    public ModelAndView handleCallsHome(HttpSession session)
    {
        int totalPages = determineTotalPagesForOutput();
        ModelAndView mav = new ModelAndView("calls_list");
        mav.addObject("pageNum", 1);
        mav.addObject("pagesTotal", totalPages);
        return mav;
    }

    private int determineTotalPagesForOutput() {
        int accounts = callDataService.getCallsCount();
        if (accounts%10 == 0)
            return accounts/10;
        else
            return (accounts/10)+1;
    }
}
