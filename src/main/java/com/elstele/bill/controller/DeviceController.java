package com.elstele.bill.controller;

import com.elstele.bill.domain.Device;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Controller
public class DeviceController {

    @RequestMapping(value="/device", method = RequestMethod.GET)
    public ModelAndView getListOfInventory(HttpSession session)
    {
        ModelAndView mav = new ModelAndView("device");
        ArrayList list = new ArrayList();
        Device firstDev = new Device();
        Device secondDev= new Device();
        Device thirdDev= new Device();

        firstDev.setName("1134");
        firstDev.setType("D-link");
        firstDev.setDesc("D-link communtitator");

        secondDev.setName("1212142124");
        secondDev.setType("Tp-link");
        secondDev.setDesc("Tp-link communtitator");

        thirdDev.setName("1134ASAS1123");
        thirdDev.setType("Asus");
        thirdDev.setDesc("Asus communtitator");

        list.add(firstDev);
        list.add(secondDev);
        list.add(thirdDev);

        mav.addObject("list", list);
        return mav;
    }
}
