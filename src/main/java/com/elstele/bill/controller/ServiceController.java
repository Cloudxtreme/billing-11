package com.elstele.bill.controller;

import com.elstele.bill.datasrv.*;
import com.elstele.bill.domain.Device;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.form.*;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.IpStatus;
import com.elstele.bill.utils.SubnetPurpose;
import com.elstele.bill.validator.ServiceValidator;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ServiceController {

    @Autowired
    private ServiceTypeDataService serviceTypeDataService;

    @Autowired
    private AccountDataService accountDataService;

    @Autowired
    private ServiceDataService serviceDataService;

    @Autowired
    private ServiceValidator serviceValidator;

    @Autowired
    private IpDataService ipDataService;

    @Autowired
    private IpSubnetDataService ipSubnetDataService;

    @Autowired
    private DeviceDataService deviceDataService;

    @RequestMapping(value="/service/account/{accountId}/{accountServiceId}/delete", method = RequestMethod.GET)
    public ModelAndView serviceDelete(@PathVariable("accountId") Integer accountId, @PathVariable("accountServiceId") Integer accountServiceId, HttpServletRequest request) {
        serviceDataService.deleteService(accountServiceId);
        ModelAndView mav = new ModelAndView("accountFull");
        AccountForm result = accountDataService.getAccountById(accountId);
        mav.addObject("accountForm", result);
        mav.addObject("successMessage", "Service was successfully deleted.");
        return mav;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(serviceValidator);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping(value="/service/account/form", method = RequestMethod.POST)
    public ModelAndView accountServiceModify(@ModelAttribute("serviceForm") @Valid ServiceForm form, BindingResult result) {
        if (result.hasErrors()) {
            List<Constants.Period> period = new ArrayList<Constants.Period>(Arrays.asList(Constants.Period.values()));
            ModelAndView mav = new ModelAndView("/account_service_form");
            form.setServiceType(serviceTypeDataService.getServiceTypeFormById(form.getServiceType().getId()));
            mav.addObject("account", accountDataService.getAccountById(form.getAccountId()));
            mav.addObject("servicePeriodList", period);
            mav.addObject("serviceTypeList", serviceTypeDataService.listServiceType());
            mav.addObject("devicesList", deviceDataService.getDevices());
            mav.addObject("ipNetList", ipSubnetDataService.getIpSubnets());
            mav.addObject("currentIpAddress", form.getServiceInternet().getIp().getId());
            mav.addObject("errorClass", "text-danger");
            return mav;
        } else {
            String message = serviceDataService.saveService(form);
            ModelAndView mav = new ModelAndView("accountFull");
            mav.addObject("successMessage", message);
            AccountForm res = accountDataService.getAccountById(form.getAccountId());
            mav.addObject("accountForm", res);
            return mav;
        }
    }

    @RequestMapping(value="/getIpAddressList/{idObj}", method = RequestMethod.POST)
    @ResponseBody
    public Map<Integer, String> ipAddressAddBySubnet(@RequestBody String json, @PathVariable("idObj") Integer idObj, HttpServletResponse response, HttpServletRequest request){
        Integer idSubNet = Integer.parseInt(json);
        Integer currIpAddressId = -1;
        if(idObj>0){
            ServiceForm serv = serviceDataService.getServiceFormById(idObj);
            currIpAddressId = serv.getServiceInternet().getIp().getId();
        }
        List<IpForm> ipFormsList = ipDataService.getBySubnetId( idSubNet );
        Map<Integer, String> ipMap= new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : ipFormsList){
            if (ipForm.getIpStatus() != IpStatus.USED  ||  (ipForm.getId()==currIpAddressId) )
            ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
        return ipMap;
    }

    @RequestMapping(value="/getDeviceFreePortList/{idObj}", method = RequestMethod.POST)
    @ResponseBody
    public List<Integer> deviceFreePortList(@RequestBody String json, @PathVariable("serviceId") Integer serviceId, HttpServletResponse response, HttpServletRequest request){
        Integer deviceId = Integer.parseInt(json);
        List<Integer> deviceFreePortList = deviceDataService.getDeviceFreePorts(deviceId);

        // Add Current Port to List
        if(serviceId>0){
            ServiceForm serviseForm = serviceDataService.getServiceFormById(serviceId);
            if( deviceId.equals(serviseForm.getServiceInternet().getDevice().getId()) )
                deviceFreePortList.add(0,serviseForm.getServiceInternet().getPort());
        }
        return deviceFreePortList;
    }


    @RequestMapping(value = "/service/account/{accountId}/{accountServiceId}/modify", method = RequestMethod.GET)
    public String serviceModify(@PathVariable("accountId") Integer accountId, @PathVariable("accountServiceId") Integer accountServiceId, HttpSession session, Map<String, Object> map) {
        List<Constants.Period> period = new ArrayList<Constants.Period>(Arrays.asList(Constants.Period.values()));
        ServiceForm form = new ServiceForm();
        Integer idCurrentIpAddress = 0;
        if (accountServiceId != 0) {
            form = serviceDataService.getServiceFormById(accountServiceId);
            idCurrentIpAddress = form.getServiceInternet().getIp().getId();
        }
        form.setAccountId(accountId);
        map.put("serviceForm", form);
        map.put("account", accountDataService.getAccountById(accountId));
        map.put("servicePeriodList", period);
        map.put("serviceTypeList", serviceTypeDataService.listServiceType());
        map.put("devicesList", deviceDataService.getDevices());
        map.put("ipNetList", ipSubnetDataService.getIpSubnets());
        map.put("currentIpAddress", idCurrentIpAddress);
        return "account_service_form";
    }

    @RequestMapping(value="/service/account/", method = RequestMethod.GET)
    public String serviceList(HttpSession session, Map<String, Object> map)
    {
        map.put("accountList", accountDataService.getAccountsList());
        return "account_service";
    }
}
