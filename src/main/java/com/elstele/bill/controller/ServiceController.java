package com.elstele.bill.controller;

import com.elstele.bill.datasrv.ServiceDataService;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.form.ServiceForm;
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
public class ServiceController {

    @Autowired
    private ServiceDataService serviceDataService;

    @Autowired
    private ServiceValidator serviceValidator;

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
    @RequestMapping(value="/service/form", method = RequestMethod.GET)
    public String serviceAdd(HttpSession session, Map<String, Object> map)
    {
        map.put("serviceForm", new ServiceForm());
        map.put("service", new ServiceInternet() );
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
            mav.addObject("successMessage", message);
            mav.addObject("serviceList", serviceDataService.listService());
            return mav;
        }

    }
    @RequestMapping(value="/service/catalog", method = RequestMethod.GET)
    public String serviceList(HttpSession session, Map<String, Object> map)
    {
        map.put("serviceList", serviceDataService.listService());
        return "service";
    }
}
