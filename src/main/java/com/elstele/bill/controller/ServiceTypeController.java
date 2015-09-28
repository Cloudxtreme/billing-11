package com.elstele.bill.controller;

import com.elstele.bill.datasrv.ServiceTypeDataService;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.validator.ServiceTypeValidator;
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
public class ServiceTypeController {

    @Autowired
    private ServiceTypeDataService serviceTypeDataService;

    @Autowired
    private ServiceTypeValidator serviceTypeValidator;

    @RequestMapping(value = "/serviceType/{id}/delete", method = RequestMethod.GET)
    public String serviceDelete(@PathVariable("id") Integer id, HttpSession session, Map<String, Object> map) {
        serviceTypeDataService.deleteServiceType(id);
        map.put("serviceTypeList", serviceTypeDataService.listServiceType());
        map.put("successMessage","Service was successfully deleted.");
        return "serviceType";
    }
    @RequestMapping(value = "/serviceType/{id}/update", method = RequestMethod.GET)
    public String serviceUpdate(@PathVariable("id") Integer id, HttpSession session, Map<String, Object> map) {
        ServiceTypeForm form = serviceTypeDataService.getServiceTypeFormById(id);
        map.put("serviceForm", form);
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
    @RequestMapping(value="/serviceType/catalog", method = RequestMethod.GET)
    public String serviceTypeList(HttpSession session, Map<String, Object> map)
    {
        map.put("serviceTypeList", serviceTypeDataService.listServiceType());
        return "serviceType";
    }
}
