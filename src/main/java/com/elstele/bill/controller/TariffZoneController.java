package com.elstele.bill.controller;


import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.utils.Messagei18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class TariffZoneController {
    @Autowired
    TariffZoneDataService dataService;
    @Autowired
    PreferenceRuleDataService preferenceRuleDataService;

    @Autowired
    Messagei18nHelper messagei18nHelper;

    @RequestMapping(value = {"/tariffzone/home", "tariffzone/all", "/tariffzone/fromdirection"}, method = RequestMethod.GET)
    public ModelAndView tariffZoneListHome(HttpServletRequest request, @RequestParam(value = "id", required = false) Integer id) {
        ModelAndView mav = new ModelAndView("tariffZoneModel");
        if (request.getRequestURI().contains("home") || (request.getRequestURI().contains("fromdirection") && dataService.checkIfObjectHasActualDate(id))) {
            mav.addObject("tariffzoneList", dataService.getOnlyActualTariffZoneList());
        } else {
            mav.addObject("tariffzoneList", dataService.getTariffZonesList());
        }
        mav.addObject("prefProfileList", preferenceRuleDataService.getRuleList());
        mav.addObject("tariffZoneForm", new TariffZoneForm());
        return mav;
    }

    @RequestMapping(value = "/tariffzone/add", method = RequestMethod.POST)
    @ResponseBody
    public TariffZoneForm addTariffZone(@ModelAttribute("tariffZoneForm") TariffZoneForm tariffZoneForm, HttpSession session) {
        dataService.create(tariffZoneForm);
        return new TariffZoneForm();
    }

    @RequestMapping(value = "/tariffzone/delete/{id}", method = RequestMethod.GET)
    public String deleteTariffZone(@PathVariable int id, RedirectAttributes redirectAttributes, HttpSession session) {
        String msg = dataService.deleteZone(id);
        redirectAttributes.addFlashAttribute(messagei18nHelper.getTypeMessage(msg), messagei18nHelper.getMessage(msg));
        return "redirect:../home";
    }

    @RequestMapping(value = "/tariffzone/edit", method = RequestMethod.GET)
    @ResponseBody
    public TariffZoneForm getZoneForEdit(@RequestParam(value = "id") int id) {
        TariffZoneForm result = dataService.getZoneById(id);
        return result;
    }

    @RequestMapping(value = "/tariffzone/edit", method = RequestMethod.POST)
    @ResponseBody
    public TariffZoneForm editZoneTariffPost(@ModelAttribute TariffZoneForm tariffZoneForm, HttpSession session) {
        dataService.updateZone(tariffZoneForm);
        return new TariffZoneForm();
    }
}
