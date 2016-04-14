package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.*;
import com.elstele.bill.domain.LocalUser;
import com.elstele.bill.form.*;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.IpStatus;
import com.elstele.bill.utils.Messagei18nHelper;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
    private TransactionDataService transactionDataService;

    @Autowired
    private IpDataService ipDataService;

    @Autowired
    private IpSubnetDataService ipSubnetDataService;

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private Messagei18nHelper messagei18nHelper;

    @RequestMapping(value = "/service/account/{accountId}/{accountServiceId}/delete", method = RequestMethod.GET)
    public ModelAndView serviceDelete(@PathVariable("accountId") Integer accountId,
                                      @PathVariable("accountServiceId") Integer accountServiceId,
                                      HttpSession session) {
        LocalUser localUser = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        serviceDataService.deleteService(accountServiceId, localUser.getUsername());
        ModelAndView mav = new ModelAndView("accountFull");
        AccountForm result = accountDataService.getAccountById(accountId);
        mav.addObject("accountForm", result);
        mav.addObject(Constants.SUCCESS_MESSAGE, messagei18nHelper.getMessage("service.success.delete"));
        return mav;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
//        binder.addValidators(serviceValidator);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping(value = "/service/account/form", method = RequestMethod.POST)
    public ModelAndView accountServiceModify(@ModelAttribute("serviceForm") @Valid ServiceForm form,
                                             BindingResult result,
                                             HttpSession session) {
        if (result.hasErrors()) {
            List<Constants.Period> period = new ArrayList<Constants.Period>(Arrays.asList(Constants.Period.values()));
            ModelAndView mav = new ModelAndView("/account_service_form");
            form.setServiceType(serviceTypeDataService.getServiceTypeFormById(form.getServiceType().getId()));
            mav.addObject("account", accountDataService.getAccountById(form.getAccountId()));
            mav.addObject("servicePeriodList", period);
            mav.addObject("devicesList", deviceDataService.getDevices());
            mav.addObject("ipNetList", ipSubnetDataService.getIpSubnets());
            mav.addObject("currentIpAddress", form.getServiceInternet().getIp().getId());
            mav.addObject("errorClass", "text-danger");
            return mav;
        } else {
            LocalUser localUser = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
            String message = serviceDataService.saveService(form, localUser.getUsername());
            ModelAndView mav = new ModelAndView("accountFull");
            mav.addObject(Constants.SUCCESS_MESSAGE, message);
            AccountForm res = accountDataService.getAccountById(form.getAccountId());
            mav.addObject("accountForm", res);
            mav.addObject("transactionList", transactionDataService.getTransactionList(res.getId(), Constants.TRANSACTION_DISPLAY_LIMIT));
            return mav;
        }
    }

    @RequestMapping(value = "/getIpAddressList/{idObj}", method = RequestMethod.POST)
    @ResponseBody
    public Map<Integer, String> ipAddressAddBySubnet(@RequestBody String json, @PathVariable("idObj") Integer idObj) {
        Integer idSubNet = Integer.parseInt(json);
        Integer currIpAddressId = serviceDataService.getCurrentIpAddressByServiceFormId(idObj);
        List<IpForm> ipFormsList = ipDataService.getBySubnetId(idSubNet);
        Map<Integer, String> ipMap = new LinkedHashMap<Integer, String>();
        for (IpForm ipForm : ipFormsList) {
            if (ipForm.getIpStatus() != IpStatus.USED || (ipForm.getId().equals(currIpAddressId)))
                ipMap.put(ipForm.getId(), ipForm.getIpName());
        }
        return ipMap;
    }

    @RequestMapping(value = "/changeSoftBlockStatus", method = RequestMethod.GET)
    @ResponseBody
    public void changeSoftBlockStatus(HttpSession session,
                                      @RequestParam(value = "serviceId") int serviceId) {
        LocalUser localUser = (LocalUser) session.getAttribute(Constants.LOCAL_USER);
        serviceDataService.changeSoftBlockStatus(serviceId, localUser.getUsername());
    }

    @RequestMapping(value = "/getDeviceFreePortList/{idObj}", method = RequestMethod.POST)
    @ResponseBody
    public List<Integer> deviceFreePortList(@RequestBody String json, @PathVariable("idObj") Integer serviceId) {
        Integer deviceId = Integer.parseInt(json);
        List<Integer> deviceFreePortList = deviceDataService.getDeviceFreePorts(deviceId);
        deviceFreePortList = serviceDataService.addCurrentDevicePortToList(deviceFreePortList, serviceId, deviceId);
        return deviceFreePortList;
    }


    @RequestMapping(value = "/service/account/{accountId}/{accountServiceId}/modify", method = RequestMethod.GET)
    public String serviceModify(@PathVariable("accountId") Integer accountId, @PathVariable("accountServiceId") Integer accountServiceId,
                                HttpSession session, Map<String, Object> map) {
        List<Constants.Period> period = new ArrayList<Constants.Period>(Arrays.asList(Constants.Period.values()));
        ServiceForm form = serviceDataService.getServiceFormById(accountServiceId);
        form.setAccountId(accountId);
        AccountForm account = accountDataService.getAccountById(accountId);

        map.put("serviceForm", form);
        map.put("account", account);
        map.put("servicePeriodList", period);
        map.put("serviceTypeList", serviceTypeDataService.listServiceTypeByBussType(account.getAccountType()));
        map.put("devicesList", deviceDataService.getDevices());
        map.put("ipNetList", ipSubnetDataService.getIpSubnets());
        map.put("currentIpAddress", serviceDataService.getCurrentIpAddress(form));
        return "account_service_form";
    }

    @RequestMapping(value = "/getCurrentIpAddress", method = RequestMethod.GET)
    @ResponseBody
    public Integer getCurrentIpAddress(HttpServletRequest request,
                                       @RequestParam(value = "serviceId") int serviceId) {
        return serviceDataService.getCurrentIpAddressByServiceFormId(serviceId);
    }

    @RequestMapping(value = "/service/account/", method = RequestMethod.GET)
    public String serviceList(Map<String, Object> map) {
        map.put("accountList", accountDataService.getAccountsList());
        return "account_service";
    }

}
