package com.elstele.bill.controller;

import com.elstele.bill.datasrv.LocalUserDataService;
import com.elstele.bill.datasrv.ServiceDataService;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceUserForm;
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
public class ServiceUserController {

    @Autowired
    private ServiceDataService serviceDataService;

/*
    @Autowired
    private ServiceUserDataService serviceUserDataService;
*/

    @Autowired
    private ServiceValidator serviceValidator;

    @Autowired
    private LocalUserDataService localUserDataService;

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
    @RequestMapping(value = "/service/user/{userId}/modify", method = RequestMethod.GET)
    public String serviceModify(@PathVariable("userId") Integer userId, HttpSession session, Map<String, Object> map) {
        LocalUser user = localUserDataService.findById(userId);
        ServiceUserForm form = new ServiceUserForm();
        form.setUser(user);
        map.put("serviceForm", form);
        map.put("user", user);
        map.put("serviceList", serviceDataService.listService());
        return "service_user_form";
    }
    @RequestMapping(value="/service/user/", method = RequestMethod.GET)
    public String serviceList(HttpSession session, Map<String, Object> map)
    {
        map.put("serviceList", serviceDataService.listService());
        return "service_user";
    }
}
