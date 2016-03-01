package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.PreferenceRuleDataService;
import com.elstele.bill.form.PreferenceRuleForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.Messagei18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
public class PreferenceRuleController {
    @Autowired
    PreferenceRuleDataService dataService;
    @Autowired
    Messagei18nHelper messagei18nHelper;

    @RequestMapping(value = {"/preferencerule/home", "/preferencerule/list"}, method = RequestMethod.GET)
    public ModelAndView getRuleList(@RequestParam(value = "prefProfileId", required = false) Integer id) {
        ModelAndView mav = new ModelAndView("ruleModel");
        mav.addObject("ruleForm", new PreferenceRuleForm());
        mav.addObject("ruleList", dataService.getRuleList());
        return mav;
    }

    @RequestMapping(value = "/preferencerule/delete/{id}", method = RequestMethod.GET)
    public String deleteRule(@PathVariable int id, RedirectAttributes redirectAttributes, HttpSession session) {
        String msg = dataService.deleteRule(id);
        redirectAttributes.addFlashAttribute(messagei18nHelper.getTypeMessage(msg), messagei18nHelper.getMessage(msg));
        return "redirect:../home";
    }

    @RequestMapping(value = "/preferencerule/add", method = RequestMethod.POST)
    @ResponseBody
    public PreferenceRuleForm addRuleForm(@ModelAttribute("ruleForm") PreferenceRuleForm preferenceRuleForm, HttpSession session) {
        dataService.createRule(preferenceRuleForm);
        return new PreferenceRuleForm();
    }

    @RequestMapping(value = "/preferencerule/edit", method = RequestMethod.GET)
    @ResponseBody
    public PreferenceRuleForm getRuleForEditing(@RequestParam(value = "id") int id) {
        return dataService.getRuleById(id);
    }

    @RequestMapping(value = "/preferencerule/edit", method = RequestMethod.POST)
    public
    @ResponseBody
    PreferenceRuleForm editRulePost(@ModelAttribute PreferenceRuleForm preferenceRuleForm, HttpSession session) {
        dataService.updateRule(preferenceRuleForm);
        return new PreferenceRuleForm();
    }

    @RequestMapping(value = "preferencerule/checkfree", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseToAjax checkFree(@RequestParam(value = "id") int id,
                             @RequestParam(value = "profileId") int profileId,
                             @RequestParam(value = "rulePriority") int rulePriority,
                             HttpSession session) {
        return dataService.checkForFree(id, profileId, rulePriority);
    }
}
