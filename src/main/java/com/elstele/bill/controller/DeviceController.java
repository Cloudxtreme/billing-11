package com.elstele.bill.controller;

import com.elstele.bill.datasrv.DeviceDataService;
import com.elstele.bill.form.DeviceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Controller
public class DeviceController {

    @Autowired
    private DeviceDataService deviceDataService;

    @RequestMapping(value="/device", method = RequestMethod.GET)
    @ResponseBody
    public List<DeviceForm> getDeviceList(HttpServletRequest request){
        List<DeviceForm> result = new ArrayList<DeviceForm>();

        result = deviceDataService.getDevices();

        return result;
    }

}
