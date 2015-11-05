package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.ServiceAssembler;
import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.dao.DeviceDAO;
import com.elstele.bill.dao.ServiceDAO;
import com.elstele.bill.dao.ServiceTypeDAO;
import com.elstele.bill.domain.*;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.utils.IpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceDataServiceImpl implements ServiceDataService {

    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private DeviceDAO deviceDAO;

    @Autowired
    private ServiceTypeDAO serviceTypeDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private IpDataService ipDataService;

    @Autowired
    private DeviceDataService deviceDataService;

    @Override
    @Transactional
    public void deleteService(Integer id) {
        serviceDAO.setStatusDelete(id);
        Service service = serviceDAO.getById(id);
        if(service instanceof ServiceInternet)
            ipDataService.setStatus(((ServiceInternet) service).getIpAddress().getId(), IpStatus.FREE);
    }

    @Override
    @Transactional
    public List<Service> listServices(){
        return serviceDAO.listServices();
    }

    @Override
    @Transactional
    public String saveService(ServiceForm form) {
        ServiceAssembler assembler = new ServiceAssembler();
        ServiceType servType = serviceTypeDAO.getById(form.getServiceType().getId());
        Service service = null;
/*
        ServiceBuilder sb = new ServiceBuilder();
        sb.setType(servType.getServiceType());
        service = sb.buildFromForm(form);
*/


        if(("internet").equals(servType.getServiceType()) ) {
//            changeIpAddressIfNeed(service,form);
            if(!form.isNew()){
                service = serviceDAO.getById(form.getId());
                if(form.getServiceInternet().getIp().getId()!=((ServiceInternet)service).getIpAddress().getId()){
                    ipDataService.setStatus(((ServiceInternet)service).getIpAddress().getId(), IpStatus.FREE);
                    ipDataService.setStatus(form.getServiceInternet().getIp().getId(), IpStatus.USED);
                }
            }
            else {
                service = new ServiceInternet();
                ipDataService.setStatus(form.getServiceInternet().getIp().getId(), IpStatus.USED);
            }
            service = assembler.fromFormToInternetBean(form, (ServiceInternet)service);
        }
        if(("phone").equals(servType.getServiceType()) ) {
            service = assembler.fromFormToPhoneBean(form);
        }
        if(("marker").equals(servType.getServiceType()) ) {
            service = assembler.fromFormToServiceBean(form);
        }
        if(service != null ) {
            service.setAccount(accountDAO.getById(form.getAccountId()));
            service.setServiceType(servType);
        }

        String message = "Service was successfully ";
        if(form.isNew()){
            serviceDAO.create(service);
            message += "added.";
        }
        else{
            serviceDAO.update(service);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public void changeIpAddressIfNeed(Service service, ServiceForm form){
    }

    @Override
    @Transactional
    public ServiceForm getServiceFormById(Integer id){
        ServiceAssembler assembler = new ServiceAssembler();
        ServiceForm result = null;
        Service bean = serviceDAO.getById(id);
        if (bean != null){
            if (bean instanceof ServiceInternet) {
                ServiceForm form = assembler.fromInternetBeanToForm((ServiceInternet)bean);
                result = form;
            }
            else if (bean instanceof ServicePhone) {
                ServiceForm form = assembler.fromPhoneBeanToForm((ServicePhone)bean);
                result = form;
            }
            else if (bean instanceof Service) {
                ServiceForm form = assembler.fromServiceBeanToForm(bean);
                result = form;
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Service getServiceBeanById(Integer id){
        return  serviceDAO.getById(id);
    }

}
