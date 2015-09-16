package com.elstele.bill.controller;

import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.dao.AccountServiceDAO;
import com.elstele.bill.datasrv.AccountDataService;
import com.elstele.bill.datasrv.AccountServiceDataService;
import com.elstele.bill.datasrv.ServiceDataService;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.AccountService;
import com.elstele.bill.domain.ServiceT;
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
    private AccountServiceDAO accountServiceDAO;

    @Autowired
    private AccountServiceDataService accountServiceDataService;

    @RequestMapping(value = "/service/account/{id}/delete", method = RequestMethod.GET)
    public String serviceDelete(@PathVariable("id") Integer idAccountService, HttpSession session, Map<String, Object> map) {
        accountServiceDataService.deleteAccountService(idAccountService);
        map.put("accountList", accountDataService.getAccountBeansList());
        map.put("successMessage","Account Service was successfully deleted.");
        return "account_service";
    }

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
            return mav;
//        }

    }
    @RequestMapping(value = "/service/account/{accountId}/{accountServiceId}/modify", method = RequestMethod.GET)
    public String serviceModify(@PathVariable("accountId") Integer accountId, @PathVariable("accountServiceId") Integer accountServiceId, HttpSession session, Map<String, Object> map) {
        Account account = accountDataService.getAccountBeanById(accountId);
        AccountServiceForm form = new AccountServiceForm();
        form.setAccountId(accountId);
        if(accountServiceId!=0){
//            form = accountServiceDataService.getAccountServiceFormById(accountServiceId);
            AccountService bean = accountServiceDataService.getAccountServiceBeanById(accountServiceId);
            form.setId(bean.getId());
            form.setDateStart(bean.getDateStart().toString());
            form.setDateEnd(bean.getDateEnd().toString());
            form.setServiceId(bean.getService().getId());
        }
        map.put("serviceForm", form);
        map.put("account", account);
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
