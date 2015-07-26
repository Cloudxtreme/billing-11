package com.elstele.bill.controller;

import com.elstele.bill.datasrv.DeviceDataService;
import com.elstele.bill.form.DeviceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Controller
public class DeviceController {

    @Autowired
    private DeviceDataService deviceDataService;

    @RequestMapping(value="/device", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDeviceList(HttpSession session){
        List<DeviceForm> result = new ArrayList<DeviceForm>();
        result = deviceDataService.getDevices();
        ModelAndView mav = new ModelAndView("device");
        mav.addObject("list", result);
        return mav;
    }

}
