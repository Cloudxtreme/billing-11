package com.elstele.bill.assembler;
import com.elstele.bill.dao.DeviceTypesDAO;
import com.elstele.bill.dao.IpDAO;
import com.elstele.bill.datasrv.DeviceDataService;
import com.elstele.bill.datasrv.DeviceTypesDataService;
import com.elstele.bill.datasrv.IpDataService;
import com.elstele.bill.domain.*;
import com.elstele.bill.form.*;
import com.elstele.bill.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.beans.BeanUtils.copyProperties;

public class ServiceAssembler{

    @Autowired
    private DeviceDataService deviceDataService;

    @Autowired
    private DeviceTypesDataService deviceTypesDataService;

    @Autowired
    private IpDataService ipDataService;

    @Autowired
    private DeviceTypesDAO deviceTypesDAO;

    @Autowired
    private IpDAO ipDAO;


    String[] propsToSkip = {"serviceInternet", "servicePhone","serviceType"};
    String[] propsToSkipInternetSrv = {"device"};

    public ServiceForm fromServiceBeanToForm(Service bean){
        ServiceForm form = new ServiceForm();
        copyProperties(bean, form, propsToSkip);
        form.setAccountId(bean.getAccount().getId());
        ServiceTypeAssembler ServiceTypeAssembler = new ServiceTypeAssembler();
        form.setServiceType(ServiceTypeAssembler.fromBeanToForm(bean.getServiceType()));
        return form;
    }


    public ServiceForm fromInternetBeanToForm(ServiceInternet bean){
        ServiceForm form = new ServiceForm();
        copyProperties(bean, form, propsToSkip);
        form.setAccountId(bean.getAccount().getId());
        form.setServiceInternet(serviceInternetFromBeanToForm((ServiceInternet) bean, form.getServiceInternet()));

        ServiceTypeAssembler ServiceTypeAssembler = new ServiceTypeAssembler();
        form.setServiceType(ServiceTypeAssembler.fromBeanToForm(bean.getServiceType()));

        if(bean.getDevice().getId() != null) {
            DeviceForm devForm = new DeviceForm();
            devForm.setId(bean.getDevice().getId());
            form.getServiceInternet().setDevice(devForm);
        }
        if(bean.getIpAddress().getId() != null) {
            IpForm ip = new IpForm();
            ip.setId(bean.getIpAddress().getId());
            form.getServiceInternet().setIp(ip);
            if(bean.getIpAddress().getIpSubnet().getId() != null) {
                IpSubnet ipSubnet = new IpSubnet();
                ipSubnet.setId(bean.getIpAddress().getIpSubnet().getId());
                form.getServiceInternet().getIp().setIpSubnet(ipSubnet);
            }
        }
         return form;
    }

    private ServiceInternetForm serviceInternetFromBeanToForm(ServiceInternet bean, ServiceInternetForm form) {
        if (bean != null){
            if (form == null){
                form = new ServiceInternetForm();
            }
            copyProperties(bean, form, propsToSkipInternetSrv);
        }
        return form;
    }

    public ServiceForm fromPhoneBeanToForm(ServicePhone bean){
        ServiceForm form = new ServiceForm();
        copyProperties(bean, form, propsToSkip);
        form.setAccountId(bean.getAccount().getId());
        form.setServicePhone(servicePhoneFromBeanToForm((ServicePhone) bean, form.getServicePhone()));

        ServiceTypeAssembler ServiceTypeAssembler = new ServiceTypeAssembler();
        form.setServiceType(ServiceTypeAssembler.fromBeanToForm(bean.getServiceType()));
        return form;
    }
    private ServicePhoneForm servicePhoneFromBeanToForm(ServicePhone bean, ServicePhoneForm form) {
        if (bean != null){
            if (form == null){
                form = new ServicePhoneForm();
            }
            copyProperties(bean, form);
        }
        return form;
    }

    public Service fromFormToServiceBean(ServiceForm form){
        Service bean = new Service();
        copyProperties(form, bean, propsToSkip);
        return bean;
    }

    public ServiceInternet fromFormToInternetBean(ServiceForm form, ServiceInternet service){
        copyProperties(form, service, propsToSkip);
        if (form.getServiceInternet() != null){
            service.setStatus(Status.ACTIVE);
            copyProperties(form.getServiceInternet(), service, propsToSkipInternetSrv);

            Device device = new Device();
            device.setId(form.getServiceInternet().getDevice().getId());
            service.setDevice(device);

            Ip ip = new Ip();
            ip.setId(form.getServiceInternet().getIp().getId());
            service.setIpAddress(ip);
        }
        return service;
    }

    public ServicePhone fromFormToPhoneBean(ServiceForm form){
        ServicePhone bean = new ServicePhone();
        copyProperties(form, bean, propsToSkip);
        if (form.getServicePhone() != null){
            bean.setStatus(Status.ACTIVE);
            copyProperties(form.getServicePhone(), bean);
        }
        return bean;
    }
}
