package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.DirectionDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.form.DirectionForm;
import com.elstele.bill.utils.Messagei18nHelper;
import com.elstele.bill.validator.DirectionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DirectionController {

    @Autowired
    DirectionDataService dataService;

    @Autowired
    TariffZoneDataService tariffZoneDataService;

    @Autowired
    DirectionValidator validator;

    @Autowired
    Messagei18nHelper messagei18nHelper;

    @RequestMapping(value = "/direction/home", method = RequestMethod.GET)
    public ModelAndView directionListHome(){
        ModelAndView mav = new ModelAndView("directionsModel");
        int totalPages = dataService.getPagesCount(10);
        mav.addObject("tariffZoneList", tariffZoneDataService.getTariffZonesList());
        mav.addObject("directionForm", new DirectionForm());
        mav.addObject("pageNum", 1);
        mav.addObject("pagesTotal", totalPages);
        return mav;
    }

    @RequestMapping(value = "/direction/list", method = RequestMethod.GET)
    @ResponseBody
    public List<DirectionForm> getDirectionList(@RequestParam(value = "rows") int rows,
                                                @RequestParam(value = "page") int page){
        return dataService.getDirectionList(page, rows);
    }

    @RequestMapping(value = "/direction/count", method = RequestMethod.POST)
    @ResponseBody
    public String getPagesCount(@RequestParam(value = "pageResults") int pageResults){
        int totalPages = dataService.getPagesCount(pageResults);
        return Integer.toString(totalPages);
    }

    @RequestMapping(value = "/direction/delete/{id}", method = RequestMethod.GET)
    public String deleteDirection(@PathVariable int id,RedirectAttributes redirectAttributes ,HttpSession session) {
        String msg = dataService.deleteDirection(id);
        redirectAttributes.addFlashAttribute(messagei18nHelper.getTypeMessage(msg), messagei18nHelper.getMessage(msg));
        return "redirect:../home";
    }

    @RequestMapping(value = "/direction/add", method = RequestMethod.POST)
    @ResponseBody
    public DirectionForm addAccountFromForm(@ModelAttribute("directionForm") DirectionForm directionForm, HttpSession session) {
        dataService.createDirection(directionForm);
        return new DirectionForm();
    }

}
