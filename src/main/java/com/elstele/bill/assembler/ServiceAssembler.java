package com.elstele.bill.assembler;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.dao.interfaces.ServiceTypeDAO;
import com.elstele.bill.datasrv.interfaces.IpDataService;
import com.elstele.bill.domain.*;
import com.elstele.bill.domain.Service;
import com.elstele.bill.form.*;
import com.elstele.bill.utils.Enums.IpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import com.elstele.bill.utils.Enums.Status;
import static com.elstele.bill.utils.Constants.*;

import static org.springframework.beans.BeanUtils.copyProperties;

@org.springframework.stereotype.Service
public class ServiceAssembler{

    @Autowired
    private IpDataService ipDataService;

    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private ServiceTypeDAO serviceTypeDAO;

    @Autowired
    private AccountDAO accountDAO;


    String[] propsToSkip = {"serviceInternet", "servicePhone","serviceType"};
    String[] propsToSkipInternetSrv = {"device"};

    public ServiceForm getServiceFormByBean (Service serviceBean){
        ServiceForm serviceForm = null;
        if (serviceBean != null){
            if (serviceBean instanceof ServiceInternet) {
                serviceForm = fromInternetBeanToForm((ServiceInternet)serviceBean);
            }
            else if (serviceBean instanceof ServicePhone) {
                serviceForm = fromPhoneBeanToForm((ServicePhone)serviceBean);
            }
            else if (serviceBean instanceof Service) {
                serviceForm = fromServiceBeanToForm(serviceBean);
            }
        }
        return serviceForm;
    }

    public Service getServiceBeanByForm(ServiceForm serviceForm){
        Service service = null;
        ServiceType servType = serviceTypeDAO.getById(serviceForm.getServiceType().getId());
        if (SERVICE_INTERNET.equals(servType.getServiceType())) {
            if (!serviceForm.isNew()) {
                service = serviceDAO.getById(serviceForm.getId());
                changeIpAddressIfNeed(service, serviceForm);
            } else {
                service = new ServiceInternet();
                ipDataService.setStatus(serviceForm.getServiceInternet().getIp().getId(), IpStatus.USED);
            }
            service = fromFormToInternetBean(serviceForm, (ServiceInternet) service);
        }
        else if (SERVICE_PHONE.equals(servType.getServiceType())) {
            service = fromFormToPhoneBean(serviceForm);
        }
        else if (SERVICE_MARKER.equals(servType.getServiceType())) {
            service = fromFormToServiceBean(serviceForm);
        }

        if (service != null) {
            service.setAccount(accountDAO.getById(serviceForm.getAccountId()));
            service.setServiceType(servType);
        }
        return service;
    }

    private void changeIpAddressIfNeed(Service service, ServiceForm form) {
        if (form.getServiceInternet().getIp().getId() != ((ServiceInternet) service).getIpAddress().getId()) {
            ipDataService.setStatus(((ServiceInternet) service).getIpAddress().getId(), IpStatus.FREE);
            ipDataService.setStatus(form.getServiceInternet().getIp().getId(), IpStatus.USED);
        }
    }

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
            ip.setIpName(bean.getIpAddress().getIpName());
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
