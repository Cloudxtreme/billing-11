package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.DeviceDataService;
import com.elstele.bill.datasrv.interfaces.DeviceTypesDataService;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.datasrv.interfaces.IpSubnetDataService;
import com.elstele.bill.form.*;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.Enums.SubnetPurpose;
import com.sun.xml.internal.ws.policy.spi.AssertionCreationException;
import org.hibernate.AssertionFailure;
import org.hibernate.HibernateException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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
    public DeviceTypesForm addDeviceType() {
        return new DeviceTypesForm();
    }


    @RequestMapping(value = "/device", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDeviceList() {
        List<DeviceForm> result = deviceDataService.getDevices();
        ModelAndView mav = new ModelAndView("deviceModel");
        mav.addObject("list", result);
        return mav;
    }

    @RequestMapping(value = "/devicetypeslist", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDeviceTypeList() {
        List<DeviceTypesForm> devType = deviceTypesDataService.getDeviceTypes();
        ModelAndView model = new ModelAndView("devicetypelistModel");
        model.addObject("devicetypelist", devType);
        return model;
    }

    @RequestMapping(value = "/adddevice", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView addDeviceFromForm() {
        ModelAndView model = new ModelAndView("adddeviceModel");
        model.addObject("deviceForm", new DeviceForm());

        List<DeviceTypesForm> devType = deviceTypesDataService.getDeviceTypes();
        Map<Integer, String> map = new HashMap<>();
        for (DeviceTypesForm deviceTypesForm : devType)
            map.put(deviceTypesForm.getId(), deviceTypesForm.getDeviceType());
        model.addObject("deviceTypesMap", map);

        List<IpForm> ipForms = ipDataService.getIpAddressList();
        Map<Integer, String> ipMap = new HashMap<>();
        for (IpForm ipForm : ipForms) {
            if (ipForm.getIpStatus() != IpStatus.USED && ipForm.getIpSubnet().getSubnetPurpose() == SubnetPurpose.MGMT)
                ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
        model.addObject("ipAddressList", ipMap);

        List<IpSubnetForm> subnetForms = ipSubnetDataService.getIpSubnets();
        Map<Integer, String> ipMapNets = new HashMap<>();
        for (IpSubnetForm ipSubnetForm : subnetForms) {
            if (ipSubnetForm.getSubnetPurpose() == SubnetPurpose.MGMT)
                ipMapNets.put(ipSubnetForm.getId(), ipSubnetForm.getIpSubnet());
        }
        model.addObject("ipNetList", ipMapNets);

        return model;
    }


    @RequestMapping(value = "/adddevice", method = RequestMethod.POST)
    public String addOrUpdateDeviceFromForm(DeviceForm deviceForm, RedirectAttributes redirectAttributes) {
        if (deviceForm.getId() == null) {
            deviceDataService.addDevice(deviceForm);
            ipDataService.setStatus(deviceForm.getIpForm().getId(), IpStatus.USED);
            redirectAttributes.addFlashAttribute("successMessage", "Device was successfully added.");
        } else {
            deviceDataService.updateDevice(deviceForm);
            ipDataService.setStatus(deviceForm.getIpForm().getId(), IpStatus.USED);
            redirectAttributes.addFlashAttribute("successMessage", "Device was successfully updated.");
        }
        return "redirect: /device.html";
    }

    @RequestMapping(value = "/device/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax deleteDevice(@RequestBody String json) {
        Integer deviceId = Integer.parseInt(json);
        return deviceDataService.deleteDevice(deviceId);
    }

    @RequestMapping(value = "/device/{id}/update", method = RequestMethod.GET)
    public ModelAndView editDevice(@PathVariable("id") int id, HttpSession session) {
        ModelAndView mav = new ModelAndView("adddeviceModel");
        DeviceForm form = deviceDataService.getById(id);
        ipDataService.setStatus(form.getIpForm().getId(), IpStatus.FREE);

        List<DeviceTypesForm> devType = deviceTypesDataService.getDeviceTypes();
        Map<Integer, String> map = new LinkedHashMap<>();
        for (DeviceTypesForm deviceTypesForm : devType)
            map.put(deviceTypesForm.getId(), deviceTypesForm.getDeviceType());

        List<IpForm> ipForms = ipDataService.getIpAddressList();
        Map<Integer, String> ipMap = new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : ipForms) {
            if (ipForm.getIpStatus() != IpStatus.USED && ipForm.getIpSubnet().getSubnetPurpose() == SubnetPurpose.MGMT)
                ipMap.put(ipForm.getId(), ipForm.getIpName());
        }

        List<IpSubnetForm> subnetForms = ipSubnetDataService.getIpSubnets();
        Map<Integer, String> ipMapNets = new LinkedHashMap<Integer, String>();
        for (IpSubnetForm ipSubnetForm : subnetForms) {
            if (ipSubnetForm.getSubnetPurpose() == SubnetPurpose.MGMT)
                ipMapNets.put(ipSubnetForm.getId(), ipSubnetForm.getIpSubnet());
        }
        mav.addObject("ipNetList", ipMapNets);
        mav.addObject("ipAddressList", ipMap);
        mav.addObject("deviceTypesMap", map);
        mav.addObject("deviceForm", form);
        return mav;
    }


    @RequestMapping(value = "/adddevicetype", method = RequestMethod.POST)
    public String doAddDeviceType(@ModelAttribute("deviceTypeModalForm") DeviceTypesForm deviceTypesForm) {
        deviceTypesDataService.addDeviceType(deviceTypesForm);
        return "redirect:/adddevice.html";
    }

    @RequestMapping(value = "/editdevicetype", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax editDeviceType(@RequestBody DeviceTypesForm deviceTypesForm) {
        try {
            deviceTypesDataService.updateDeviceTypes(deviceTypesForm);
            return ResponseToAjax.SUCCESS;
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseToAjax.ERROR;
        }
    }


    @RequestMapping(value = "**/getValidIps", method = RequestMethod.POST)
    @ResponseBody
    public Map<Integer, String> ipAddressAddBySubnet(@RequestBody String json) {
        Integer id = Integer.parseInt(json);
        List<IpForm> list = ipDataService.getBySubnetId(id);
        Map<Integer, String> ipMap = new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : list) {
            if (ipForm.getIpStatus() != IpStatus.USED)
                ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
        return ipMap;
    }

    @RequestMapping(value = "**/returniplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, String> ipAddressListReturn() {
        List<IpForm> ipForms = ipDataService.getIpAddressList();
        Map<Integer, String> ipMap = new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : ipForms) {
            if (ipForm.getIpStatus() != IpStatus.USED && ipForm.getIpSubnet().getSubnetPurpose() == SubnetPurpose.MGMT)
                ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
        return ipMap;
    }

}
