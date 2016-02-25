package com.elstele.bill.controller;


import com.elstele.bill.datasrv.interfaces.TariffZoneDataService;
import com.elstele.bill.form.TariffZoneForm;
import com.elstele.bill.utils.Messagei18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class TariffZoneController {
    @Autowired
    TariffZoneDataService dataService;

    @Autowired
    Messagei18nHelper messagei18nHelper;

    @RequestMapping(value = "/tariffzone/home", method = RequestMethod.GET)
    public ModelAndView directionListHome(){
        ModelAndView mav = new ModelAndView("tariffZoneModel");
        mav.addObject("tariffzoneList", dataService.getTariffZonesList());
        mav.addObject("tariffZoneForm", new TariffZoneForm());
        return mav;
    }

    @RequestMapping(value = "/tariffzone/add", method = RequestMethod.POST)
    @ResponseBody
    public TariffZoneForm addTariffZone(@ModelAttribute("directionForm") TariffZoneForm TariffZoneForm, HttpSession session) {
        dataService.create(TariffZoneForm);
        return new TariffZoneForm();
    }

    @RequestMapping(value = "/tariffzone/delete/{id}", method = RequestMethod.GET)
    public String deleteTariffZone(@PathVariable int id,RedirectAttributes redirectAttributes ,HttpSession session) {
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

    @RequestMapping(value = "/tariffzone/fromdirection", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView openModalWithDataAfterRedirect(@RequestParam(value = "id") int id) {
        ModelAndView mav = new ModelAndView("tariffZoneModel");
        mav.addObject("tariffzoneList", dataService.getTariffZonesList());
        mav.addObject("tariffZoneForm", dataService.getZoneById(id));
        return mav;
    }

    @RequestMapping(value = "/tariffzone/edit", method = RequestMethod.POST)
    @ResponseBody
    public TariffZoneForm editZoneTariffPost(@ModelAttribute TariffZoneForm tariffZoneForm, HttpSession session) {
        dataService.updateZone(tariffZoneForm);
        return new TariffZoneForm();
    }

    @RequestMapping(value = "/tariffzone/changeSoftBlockStatus", method = RequestMethod.GET)
    @ResponseBody
    public void changeSoftBlockStatus(HttpSession session,
                                      @RequestParam(value = "tariffzoneid") int zoneId) {
        dataService.changeSoftBlockStatus(zoneId);
    }
}
