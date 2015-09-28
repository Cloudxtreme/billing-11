package com.elstele.bill.controller;

import com.elstele.bill.datasrv.AccountDataService;
import com.elstele.bill.datasrv.ServiceDataService;
import com.elstele.bill.datasrv.ServiceTypeDataService;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceInternetForm;
import com.elstele.bill.validator.ServiceValidator;
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
public class ServiceController {

    @Autowired
    private ServiceTypeDataService serviceTypeDataService;

    @Autowired
    private AccountDataService accountDataService;

    @Autowired
    private ServiceDataService serviceDataService;

    @Autowired
    private ServiceValidator serviceValidator;

    @RequestMapping(value = "/service/account/{id}/delete", method = RequestMethod.GET)
    public String serviceDelete(@PathVariable("id") Integer idAccountService, HttpSession session, Map<String, Object> map) {
        serviceDataService.deleteService(idAccountService);
        map.put("accountList", accountDataService.getAccountsList());
        map.put("successMessage","Account Service was successfully deleted.");
        return "account_service";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(serviceValidator);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping(value="/service/account/form", method = RequestMethod.POST)
    public ModelAndView accountServiceModify(@ModelAttribute("serviceForm") @Valid ServiceForm form, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView mav = new ModelAndView("/account_service_form");
            form.setServiceType(serviceTypeDataService.getServiceTypeFormById(form.getServiceType().getId()));
            mav.addObject("accountList", accountDataService.getAccountsList());
            mav.addObject("account", accountDataService.getAccountById(form.getAccountId()));
            mav.addObject("serviceTypeList", serviceTypeDataService.listServiceType());
            mav.addObject("errorClass", "text-danger");
            return mav;
        } else {
            String message = serviceDataService.saveService(form);
            ModelAndView mav = new ModelAndView("account_service");
            mav.addObject("successMessage", message);
            mav.addObject("accountList", accountDataService.getAccountsList());
            return mav;

        }
    }
    @RequestMapping(value = "/service/account/{accountId}/{accountServiceId}/modify", method = RequestMethod.GET)
    public String serviceModify(@PathVariable("accountId") Integer accountId, @PathVariable("accountServiceId") Integer accountServiceId, HttpSession session, Map<String, Object> map) {
        ServiceForm form = new ServiceForm();
        if (accountServiceId != 0) {
            form = serviceDataService.getServiceFormById(accountServiceId);
        }
        form.setAccountId(accountId);
        map.put("serviceForm", form);
        map.put("account", accountDataService.getAccountById(accountId));
        map.put("serviceTypeList", serviceTypeDataService.listServiceType());
        return "account_service_form";
    }
    @RequestMapping(value="/service/account/", method = RequestMethod.GET)
    public String serviceList(HttpSession session, Map<String, Object> map)
    {
        map.put("accountList", accountDataService.getAccountsList());
        return "account_service";
    }
}
