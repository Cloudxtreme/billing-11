package com.elstele.bill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;



@Controller
public class DeviceController {

    @RequestMapping(value="/device", method = RequestMethod.GET)
    public String getListOfInventory(HttpSession session)
    {

        return "device";
    }
}
