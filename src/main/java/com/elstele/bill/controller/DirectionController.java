package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.form.DirectionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DirectionController {

    @Autowired
    DirectionDataService dataService;

    @RequestMapping(value = "/directionhome", method = RequestMethod.GET)
    public ModelAndView directionListHome(){
        ModelAndView mav = new ModelAndView("directionsModel");
        int totalPages = dataService.getPagesCount(10);
        mav.addObject("pageNum", 1);
        mav.addObject("pagesTotal", totalPages);
        return mav;
    }

    @RequestMapping(value = "/directionlist", method = RequestMethod.GET)
    @ResponseBody
    public List<DirectionForm> getDirectionList(@RequestParam(value = "rows") int rows,
                                                @RequestParam(value = "page") int page){
        return dataService.getDirectionList(page, rows);
    }

    @RequestMapping(value = "/directionCount", method = RequestMethod.POST)
    @ResponseBody
    public String getPagesCount(@RequestParam(value = "pageResults") int pageResults){
        int totalPages = dataService.getPagesCount(pageResults);
        return Integer.toString(totalPages);
    }
}
