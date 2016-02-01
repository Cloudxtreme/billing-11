package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.DeviceDataService;
import com.elstele.bill.datasrv.interfaces.DeviceTypesDataService;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.datasrv.interfaces.IpSubnetDataService;
import com.elstele.bill.form.*;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.Messagei18nHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

import static com.elstele.bill.utils.Constants.SUCCESS_MESSAGE;

@Controller
@SessionAttributes("deviceForm")
public class DeviceController {

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private DeviceTypesDataService deviceTypesDataService;

    @Autowired
    private IpDataService ipDataService;

    @Autowired
    private IpSubnetDataService ipSubnetDataService;

    @Autowired
    private Messagei18nHelper messageHelper;

    final static Logger log = LogManager.getLogger(DeviceController.class);

    @ModelAttribute("deviceTypeModalForm")
    public DeviceTypesForm addDeviceType() {
        return new DeviceTypesForm();
    }


    @RequestMapping(value = "/device", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDeviceList() {
        log.info("getDeviceList fired");
        List<DeviceForm> result = deviceDataService.getDevices();
        ModelAndView mav = new ModelAndView("deviceModel");
        mav.addObject("list", result);
        return mav;
    }

    @RequestMapping(value = "/devicetypeslist", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getDeviceTypeList() {
        log.info("getDeviceTypeList started");
        ModelAndView model = new ModelAndView("devicetypelistModel");
        model.addObject("devicetypelist", deviceTypesDataService.getDeviceTypes());
        return model;
    }

    @RequestMapping(value = "/adddevice", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView addDeviceFromForm() {
        ModelAndView model = new ModelAndView("adddeviceModel");
        model.addObject("deviceForm", new DeviceForm());
        model.addObject("deviceTypesMap", deviceTypesDataService.getDeviceTypesAsMap());
        model.addObject("ipAddressList", ipDataService.getIpAddressAsMap());
        model.addObject("ipNetList", ipSubnetDataService.getIpSubnetsAsMap());
        return model;
    }


    @RequestMapping(value = "/adddevice", method = RequestMethod.POST)
    public String addOrUpdateDeviceFromForm(@ModelAttribute(value = "deviceForm") DeviceForm deviceForm, RedirectAttributes redirectAttributes) {
        String msg;
        if (deviceForm.getId() == null) {
            deviceDataService.addDevice(deviceForm);
            msg = messageHelper.getMessage(Constants.DEVICE_ADD_SUCCESS);
        } else {
            deviceDataService.updateDevice(deviceForm);
            msg = messageHelper.getMessage(Constants.DEVICE_UPDATE_SUCCESS);
        }
        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE, msg);
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

        mav.addObject("ipNetList", ipSubnetDataService.getIpSubnetsAsMap());
        mav.addObject("ipAddressList", ipDataService.getIpAddressAsMap());
        mav.addObject("deviceTypesMap", deviceTypesDataService.getDeviceTypesAsMap());
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
    public Map<Integer, String> ipAddressAddBySubnet(@RequestBody String subnetId) {
       return ipDataService.getIpMapBySubnets(subnetId);
    }

    @RequestMapping(value = "**/returniplist", method = RequestMethod.GET)
    @ResponseBody
    public Map<Integer, String> ipAddressListReturn() {
        return ipDataService.getIpAddressAsMap();
    }

}
