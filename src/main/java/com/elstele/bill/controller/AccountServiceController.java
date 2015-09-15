package com.elstele.bill.controller;

import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.datasrv.AccountDataService;
import com.elstele.bill.datasrv.AccountServiceDataService;
import com.elstele.bill.datasrv.ServiceDataService;
import com.elstele.bill.domain.Account;
import com.elstele.bill.form.AccountServiceForm;
import com.elstele.bill.validator.ServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class AccountServiceController {

    @Autowired
    private ServiceDataService serviceDataService;

    @Autowired
    private ServiceValidator serviceValidator;

    @Autowired
    private AccountDataService accountDataService;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private AccountServiceDataService accountServiceDataService;
    /*
    @RequestMapping(value = "/service/{id}/delete", method = RequestMethod.GET)
    public String serviceDelete(@PathVariable("id") Integer id, HttpSession session, Map<String, Object> map) {
        serviceDataService.deleteService(id);
        map.put("serviceList", serviceDataService.listService());
        map.put("successMessage","Service was successfully deleted.");
        return "service";
    }
    @RequestMapping(value = "/service/{id}/update", method = RequestMethod.GET)
    public String serviceUpdate(@PathVariable("id") Integer id, HttpSession session, Map<String, Object> map) {
        ServiceForm form = serviceDataService.getServiceFormById(id);
        map.put("serviceForm", form);
        return "service_form";

    }
    @RequestMapping(value="/service/form", method = RequestMethod.POST)
    public ModelAndView serviceAdd(@ModelAttribute("serviceForm") ServiceForm form, BindingResult result){

        serviceValidator.validate(form, result);
        if (result.hasErrors()){
            ModelAndView mav = new ModelAndView("/service_form");
            mav.addObject("errorClass", "text-danger");
            return mav;
        }
        else{
            String message = serviceDataService.saveService(form);
            ModelAndView mav = new ModelAndView("service");
 0           mav.addObject("successMessage", message);
            mav.addObject("serviceList", serviceDataService.listService());
            return mav;
        }

    }
*/
    @RequestMapping(value="/service/account/form", method = RequestMethod.POST)
    public ModelAndView accountServiceModify(@ModelAttribute("serviceForm") AccountServiceForm form, BindingResult result){
/*
        serviceValidator.validate(form, result);
        if (result.hasErrors()){
            ModelAndView mav = new ModelAndView("/account_service_form");
            mav.addObject("errorClass", "text-danger");
            return mav;
        }
        else{
*/
            String message = accountServiceDataService.saveAccountService(form);
            ModelAndView mav = new ModelAndView("account_service");
            mav.addObject("successMessage", message);
            mav.addObject("accountList", accountDataService.getAccountBeansList());
            mav.addObject("accountService", accountServiceDataService.listAccountServices());
            return mav;
//        }

    }
    @RequestMapping(value = "/service/account/{accountId}/modify", method = RequestMethod.GET)
    public String serviceModify(@PathVariable("accountId") Integer accountId, HttpSession session, Map<String, Object> map) {
        Account account = accountDataService.getAccountBeanById(accountId);
        AccountServiceForm form = new AccountServiceForm();
        form.setAccountId(accountId);
        map.put("serviceForm", form);
        map.put("account", account);
        map.put("serviceList", serviceDataService.listService());
        return "account_service_form";
    }
    @RequestMapping(value="/service/account/", method = RequestMethod.GET)
    public String serviceList(HttpSession session, Map<String, Object> map)
    {
        map.put("accountList", accountDataService.getAccountBeansList());
        map.put("accountService", accountServiceDataService.listAccountServices());
        return "account_service";
    }
}
