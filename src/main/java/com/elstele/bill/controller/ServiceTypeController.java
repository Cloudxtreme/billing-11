package com.elstele.bill.controller;

import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.datasrv.interfaces.ServiceTypeDataService;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.MessageLanguageDeterminant;
import com.elstele.bill.validator.ServiceAttributeValidator;
import com.elstele.bill.validator.ServiceTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ServiceTypeController {

    @Autowired
    private ServiceTypeDataService serviceTypeDataService;
    @Autowired
    private ServiceTypeValidator serviceTypeValidator;
    @Autowired
    private ServiceAttributeValidator serviceAttributeValidator;

    @RequestMapping(value = "/serviceType/{id}/delete", method = RequestMethod.GET)
    public String serviceDelete(@PathVariable("id") Integer id, HttpSession session, Map<String, Object> map) {
        serviceTypeDataService.deleteServiceType(id);
        map.put("serviceTypeList", serviceTypeDataService.listServiceType());
        map.put("successMessage", MessageLanguageDeterminant.determine("serviceDelete"));
        return "serviceType";
    }
    @RequestMapping(value = "/serviceType/{id}/update", method = RequestMethod.GET)
    public String serviceUpdate(@PathVariable("id") Integer id, HttpSession session, Map<String, Object> map) {
        ServiceTypeForm form = serviceTypeDataService.getServiceTypeFormById(id);
        List<Constants.AccountType> bussTypes = new ArrayList<Constants.AccountType>(Arrays.asList(Constants.AccountType.values()));
        map.put("serviceForm", form);
        map.put("serviceInternetAttributeList", serviceTypeDataService.listServiceAttribute(form.getId()));
        map.put("bussTypes", bussTypes);
        return "serviceType_form";

    }
    @RequestMapping(value="/serviceType/form", method = RequestMethod.GET)
    public String serviceTypeAdd(HttpSession session, Map<String, Object> map)
    {
        map.put("serviceForm", new ServiceTypeForm());
        return "serviceType_form";
    }

    @RequestMapping(value="/serviceType/form", method = RequestMethod.POST)
    public ModelAndView serviceAdd(@ModelAttribute("serviceForm") ServiceTypeForm form, BindingResult result){

        serviceTypeValidator.validate(form, result);
        if (result.hasErrors()){
            ModelAndView mav = new ModelAndView("serviceType_form");
            mav.addObject("errorClass", "text-danger");
            return mav;
        }
        else{
            String message = serviceTypeDataService.saveServiceType(form);
            ModelAndView mav = new ModelAndView("serviceType");
            mav.addObject("successMessage", message);
            mav.addObject("serviceTypeList", serviceTypeDataService.listServiceType());
            return mav;
        }

    }

    @RequestMapping(value = "/serviceAttribute/{serviceId}/{serviceAttributeId}/delete", method = RequestMethod.GET)
    public String serviceAttributeDelete(@PathVariable("serviceId") Integer serviceId, @PathVariable("serviceAttributeId") Integer serviceAttributeId, HttpSession session, Map<String, Object> map) {
        serviceTypeDataService.deleteServiceAttribute(serviceAttributeId);
        map.put("serviceForm", serviceTypeDataService.getServiceTypeFormById(serviceId));
        map.put("serviceInternetAttributeList", serviceTypeDataService.listServiceAttribute(serviceId));
        map.put("successMessage", MessageLanguageDeterminant.determine("serviceAttributeDelete"));
        return "serviceType_form";
    }
    @RequestMapping(value="/serviceAttribute/modify", method = RequestMethod.POST)
    public ModelAndView serviceAttributeModify(@ModelAttribute("serviceAttributeForm") ServiceInternetAttributeForm form, BindingResult result)
    {
        serviceAttributeValidator.validate(form, result);
        if (result.hasErrors()){
            ModelAndView mav = new ModelAndView("serviceAttributeForm");
            mav.addObject("errorClass", "text-danger");
            return mav;
        }
        else{
            String message = serviceTypeDataService.saveServiceAttribute(form);
            ModelAndView mav = new ModelAndView("serviceType_form");
            mav.addObject("serviceForm", serviceTypeDataService.getServiceTypeFormById(form.getServiceTypeId()));
            mav.addObject("serviceInternetAttributeList", serviceTypeDataService.listServiceAttribute(form.getServiceTypeId()));
            mav.addObject("successMessage", message);
            return mav;
        }
    }
    @RequestMapping(value = "/serviceAttribute/{serviceId}/{serviceAttributeId}/modify", method = RequestMethod.GET)
    public String serviceAttributeModify(@PathVariable("serviceId") Integer serviceId, @PathVariable("serviceAttributeId") Integer serviceAttributeId, HttpSession session, Map<String, Object> map)
    {
        ServiceInternetAttributeForm serviceAttributeForm = serviceTypeDataService.getServiceAttributeForm(serviceAttributeId, serviceId);
        map.put("serviceAttributeForm", serviceAttributeForm);
        return "serviceAttributeForm";
    }

    @RequestMapping(value = "/serviceTypeList", method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, String> getServiceTypeList(HttpServletRequest request,
                                                      @RequestParam(value = "type") String type) {
        List<ServiceType> list = serviceTypeDataService.listServiceType(type);
        Map<Integer, String> ipMap = new LinkedHashMap();
        for (ServiceType serviceType : list){
            ipMap.put(serviceType.getId(), serviceType.getName() + " (" + serviceType.getPrice() + " грн.)");
        }
        return ipMap;
    }

    @RequestMapping(value="/serviceType/catalog", method = RequestMethod.GET)
    public String serviceTypeList(HttpSession session, Map<String, Object> map)
    {
        map.put("serviceTypeList", serviceTypeDataService.listServiceType());
        return "serviceType";
    }
}
