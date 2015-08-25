package com.elstele.bill.controller;

import com.elstele.bill.datasrv.DeviceDataService;
import com.elstele.bill.datasrv.DeviceTypesDataService;
import com.elstele.bill.datasrv.IpDataService;
import com.elstele.bill.datasrv.IpSubnetDataService;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.form.IpSubnetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;


@Controller
public class DeviceController {

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private DeviceTypesDataService deviceTypesDataService;

    @Autowired
    private IpDataService ipDataService;

    @Autowired
    private IpSubnetDataService ipSubnetDataService;


    @ModelAttribute("deviceTypeModalForm")
    public DeviceTypesForm addDeviceType(){
        return new DeviceTypesForm();
    }


    @RequestMapping(value="/device", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDeviceList(HttpSession session){
        List<DeviceForm> result = new ArrayList<DeviceForm>();
        result = deviceDataService.getDevices();
        ModelAndView mav = new ModelAndView("device");
        mav.addObject("list", result);
        return mav;
    }

    @RequestMapping(value="/adddevice", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView addDeviceFromForm(){
        ModelAndView model = new ModelAndView("adddevice");
        model.addObject("deviceForm",new DeviceForm());

        //Adding deviceTypes list to the Select
        List<DeviceTypesForm> devType = new ArrayList<DeviceTypesForm>();
        devType = deviceTypesDataService.getDeviceTypes();
        Map<Integer, String> map= new LinkedHashMap<Integer, String>();
        for (DeviceTypesForm deviceTypesForm : devType) map.put(deviceTypesForm.getId(), deviceTypesForm.getDeviceType());
        model.addObject("deviceTypesMap", map);

        //Adding Ip-addresses to the Select
        List<IpForm> ipForms  = new ArrayList<IpForm>();
        ipForms = ipDataService.getIpAddressList();
        Map<Integer, String> ipMap= new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : ipForms) ipMap.put(ipForm.getId(), ipForm.getIpName());
        model.addObject("ipAddressList", ipMap);

        //Adding ip-nets list to the Select
        List<IpSubnetForm> subnetForms = new ArrayList<IpSubnetForm>();
        subnetForms = ipSubnetDataService.getIpSubnets();
        Map<Integer, String> ipMapNets = new LinkedHashMap<Integer, String>();
        for (IpSubnetForm ipSubnetForm : subnetForms) ipMapNets.put(ipSubnetForm.getId(), ipSubnetForm.getIpSubnet());
        model.addObject("ipNetList", ipMapNets);



        return model;
    }


    @RequestMapping(value="/adddevice", method = RequestMethod.POST)
    @ResponseBody
     public ModelAndView addOrUpdateDeviceFromForm(DeviceForm deviceForm, HttpServletRequest request){
        if (deviceForm.getId() == null ) {
            deviceDataService.addDevice(deviceForm);
        } else {
            deviceDataService.updateDevice(deviceForm);
        }

        //redirect to the devices list after deleting
        List<DeviceForm> result = new ArrayList<DeviceForm>();
        result = deviceDataService.getDevices();
        ModelAndView mav = new ModelAndView("device");
        mav.addObject("list", result);
        return mav;
    }

    @RequestMapping(value="/device/{id}/delete", method = RequestMethod.GET)
    public ModelAndView deleteDevice(@PathVariable("id") int id, HttpSession session){

        deviceDataService.deleteDevice(id);

        //redirect to the devices list after deleting
        List<DeviceForm> result = new ArrayList<DeviceForm>();
        result = deviceDataService.getDevices();
        ModelAndView mav = new ModelAndView("device");
        mav.addObject("list", result);
        return mav;
    }

    @RequestMapping(value="/device/{id}/update", method = RequestMethod.GET)
    public ModelAndView editDevice(@PathVariable("id") int id, HttpSession session){
        ModelAndView mav = new ModelAndView("adddevice");
        DeviceForm form = new DeviceForm();
        form = deviceDataService.getById(id);

        //Device Types extracting to the form for redact
        List<DeviceTypesForm> devType = new ArrayList<DeviceTypesForm>();
        devType = deviceTypesDataService.getDeviceTypes();
        Map<Integer, String> map= new LinkedHashMap<Integer, String>();
        for (DeviceTypesForm deviceTypesForm : devType) map.put(deviceTypesForm.getId(), deviceTypesForm.getDeviceType());

        //Ip address extracting to the form for redact
        List<IpForm> ipForms  = new ArrayList<IpForm>();
        ipForms = ipDataService.getIpAddressList();
        Map<Integer, String> ipMap= new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : ipForms) ipMap.put(ipForm.getId(), ipForm.getIpName());

        //Ip-nets extracting to the form for redact
        List<IpSubnetForm> subnetForms = new ArrayList<IpSubnetForm>();
        subnetForms = ipSubnetDataService.getIpSubnets();
        Map<Integer, String> ipMapNets = new LinkedHashMap<Integer, String>();
        for (IpSubnetForm ipSubnetForm : subnetForms) ipMapNets.put(ipSubnetForm.getId(), ipSubnetForm.getIpSubnet());

        mav.addObject("ipNetList", ipMapNets);
        mav.addObject("ipAddressList", ipMap);
        mav.addObject("deviceTypesMap", map);
        mav.addObject("deviceForm", form);
        return mav;
    }


    @RequestMapping(value="/adddevicetype", method = RequestMethod.POST)
    public String doAddDeviceType(@ModelAttribute("deviceTypeModalForm") DeviceTypesForm deviceTypesForm, HttpServletResponse response){
        DeviceTypesForm elseDeviceTypesform = new DeviceTypesForm();
        elseDeviceTypesform.setDeviceType(deviceTypesForm.getDeviceType());
        deviceTypesDataService.addDeviceType(elseDeviceTypesform);
        return "redirect:/adddevice.html";
    }

}
