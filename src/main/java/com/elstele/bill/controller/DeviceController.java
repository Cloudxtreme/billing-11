package com.elstele.bill.controller;

import com.elstele.bill.datasrv.DeviceDataService;
import com.elstele.bill.datasrv.DeviceTypesDataService;
import com.elstele.bill.datasrv.IpDataService;
import com.elstele.bill.datasrv.IpSubnetDataService;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.DeviceTypes;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.form.DeviceForm;
import com.elstele.bill.form.DeviceTypesForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.form.IpSubnetForm;
import com.elstele.bill.utils.Status;
import org.omg.PortableInterceptor.ACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        for (IpForm ipForm : ipForms)
        {if (ipForm.getStatus() != Status.ACTIVE)
            ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
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
     public String addOrUpdateDeviceFromForm(DeviceForm deviceForm, HttpServletRequest request){
        Status status = Status.ACTIVE;
        String result ="";
        try{
        if (deviceForm.getId() == null) {
            deviceDataService.addDevice(deviceForm);
            ipDataService.setStatus(deviceForm.getIpForm().getId(), status);
        } else {
            deviceDataService.updateDevice(deviceForm);
            ipDataService.setStatus(deviceForm.getIpForm().getId(), status);
        }
            result = "redirect:/device.html";
         }catch (NullPointerException e){
            System.out.println(e);
            result = "redirect:/404.html";
        }


        return result;

        //redirect to the devices list after deleting
        /*List<DeviceForm> result = new ArrayList<DeviceForm>();
        result = deviceDataService.getDevices();
        ModelAndView mav = new ModelAndView("device");
        mav.addObject("list", result);
        return mav;*/
    }

    @RequestMapping(value="/device/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDevice(@RequestBody String json, HttpSession session, HttpServletResponse response, HttpServletRequest request){
       /* String numberOnly= json.replaceAll("[^0-9]", "");*/
        Integer id = Integer.parseInt(json);
        String result = "";

        Status status = Status.DELETED;
        DeviceForm deviceForm = deviceDataService.getById(id);
        try {
            if (deviceForm.getIpForm().getId() != null) {
                ipDataService.setStatus(deviceForm.getIpForm().getId(), status);
            }
            deviceDataService.deleteDevice(id);
            result = "success";

        } catch (NullPointerException e) {
            String error = e.getMessage();
            result = "error";
        }

        return result;
    }

    @RequestMapping(value="/device/{id}/update", method = RequestMethod.GET)
    public ModelAndView editDevice(@PathVariable("id") int id, HttpSession session){
        ModelAndView mav = new ModelAndView("adddevice");
        DeviceForm form = new DeviceForm();
        form = deviceDataService.getById(id);
        ipDataService.setStatus(form.getIpForm().getId(), Status.INACTIVE);


        //Device Types extracting to the form for redact
        List<DeviceTypesForm> devType = new ArrayList<DeviceTypesForm>();
        devType = deviceTypesDataService.getDeviceTypes();
        Map<Integer, String> map= new LinkedHashMap<Integer, String>();
        for (DeviceTypesForm deviceTypesForm : devType) map.put(deviceTypesForm.getId(), deviceTypesForm.getDeviceType());

        //Ip address extracting to the form for redact
        List<IpForm> ipForms  = new ArrayList<IpForm>();
        ipForms = ipDataService.getIpAddressList();
        Map<Integer, String> ipMap= new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : ipForms)
        {if (ipForm.getStatus() != Status.ACTIVE)
            ipMap.put(ipForm.getId(), ipForm.getIpName());
        }

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


    @RequestMapping(value="/getValidIps", method = RequestMethod.POST)
    @ResponseBody
    public Map<Integer, String> ipAddressAddBySubnet(@RequestBody String json, HttpServletResponse response, HttpServletRequest request){
        Integer id = Integer.parseInt(json);
        List<IpForm> list = ipDataService.getBySubnetId(id);
        Map<Integer, String> ipMap= new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : list)
        {if (ipForm.getStatus() != Status.ACTIVE)
            ipMap.put(ipForm.getId(), ipForm.getIpName());
        }

        return ipMap;
    }

    @RequestMapping(value="returniplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, String> ipAddressListReturn(){
        List<IpForm> ipForms  = new ArrayList<IpForm>();
        ipForms = ipDataService.getIpAddressList();
        Map<Integer, String> ipMap= new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : ipForms)
        {if (ipForm.getStatus() != Status.ACTIVE)
            ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
        return ipMap;
    }


}
