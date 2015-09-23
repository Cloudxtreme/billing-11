package com.elstele.bill.controller;

import com.elstele.bill.datasrv.AccountDataService;
import com.elstele.bill.datasrv.AccountServiceDataService;
import com.elstele.bill.datasrv.ServiceDataService;
import com.elstele.bill.form.AccountServiceForm;
import com.elstele.bill.validator.AccountServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Controller
public class AccountServiceController {

    @Autowired
    private ServiceDataService serviceDataService;

    @Autowired
    private AccountDataService accountDataService;

    @Autowired
    private AccountServiceDataService accountServiceDataService;

    @Autowired
    private AccountServiceValidator accountServiceValidator;

    @RequestMapping(value = "/service/account/{id}/delete", method = RequestMethod.GET)
    public String serviceDelete(@PathVariable("id") Integer idAccountService, HttpSession session, Map<String, Object> map) {
        accountServiceDataService.deleteAccountService(idAccountService);
        map.put("accountList", accountDataService.getAccountBeansList());
        map.put("successMessage","Account Service was successfully deleted.");
        return "account_service";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(accountServiceValidator);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping(value="/service/account/form", method = RequestMethod.POST)
    public ModelAndView accountServiceModify(@ModelAttribute("serviceForm") @Valid AccountServiceForm form, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("/account_service_form");
            mav.addObject("serviceList", serviceDataService.listService());
            mav.addObject("errorClass", "text-danger");
            return mav;
        } else {
            String message = accountServiceDataService.saveAccountService(form);
            ModelAndView mav = new ModelAndView("account_service");
            mav.addObject("successMessage", message);
            mav.addObject("accountList", accountDataService.getAccountBeansList());
            return mav;

        }
    }
    @RequestMapping(value = "/service/account/{accountId}/{accountServiceId}/modify", method = RequestMethod.GET)
    public String serviceModify(@PathVariable("accountId") Integer accountId, @PathVariable("accountServiceId") Integer accountServiceId, HttpSession session, Map<String, Object> map) {
        AccountServiceForm form = new AccountServiceForm();
        if (accountServiceId != 0)
            form = accountServiceDataService.getAccountServiceFormById(accountServiceId);
        else
            form.setAccount(accountDataService.getAccountById(accountId));
        map.put("serviceForm", form);
        map.put("serviceList", serviceDataService.listService());
        return "account_service_form";
    }
    @RequestMapping(value="/service/account/", method = RequestMethod.GET)
    public String serviceList(HttpSession session, Map<String, Object> map)
    {
        map.put("accountList", accountDataService.getAccountBeansList());
        return "account_service";
    }
}
