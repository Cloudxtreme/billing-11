package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.ServiceAssembler;
import com.elstele.bill.domain.*;
import com.elstele.bill.dao.interfaces.AccountDAO;
import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.dao.interfaces.ServiceTypeDAO;
import com.elstele.bill.datasrv.interfaces.ServiceDataService;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServicePhone;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.form.ServiceTypeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceDataServiceImpl implements ServiceDataService {

    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private ServiceAssembler serviceAssembler;

    @Override
    @Transactional
    public void deleteService(Integer id, String changerName) {
        serviceDAO.deleteService(id, changerName);
    }

    @Override
    @Transactional
    public String saveService(ServiceForm form, String changerName) {
        form = modifyServiceTypeIfNeed(form, changerName);
        Service service = serviceAssembler.getServiceBeanByForm(form);
        return serviceDAO.saveService(service, form.isNew(), changerName);
    }

    @Override
    @Transactional
    public ServiceForm getServiceFormById(Integer serviceId) {
        ServiceForm serviceForm = new ServiceForm();
        if( serviceId>0 ) {
            Service serviceBean = serviceDAO.getById(serviceId);
            serviceForm = serviceAssembler.getServiceFormByBean(serviceBean);
        }
        return serviceForm;
    }

    @Override
    public Integer getCurrentIpAddress (ServiceForm serviceForm){
        Integer currentIpAddress = 0;
        if (serviceForm.getServiceInternet().getIp() != null) {
            currentIpAddress = serviceForm.getServiceInternet().getIp().getId();
        }
        return currentIpAddress;
    }

    @Override
    @Transactional
    public Integer getCurrentIpAddressByServiceFormId(Integer serviceFormId){
        ServiceForm serv = getServiceFormById(serviceFormId);
        return getCurrentIpAddress(serv);
    }

    @Override
    @Transactional
    public List<Integer> addCurrentDevicePortToList(List<Integer> deviceFreePortList, Integer serviceId, Integer deviceId){
        if(serviceId>0){
            ServiceForm serviceForm = getServiceFormById(serviceId);
            if( deviceId.equals(serviceForm.getServiceInternet().getDevice().getId()) )
                deviceFreePortList.add(0,serviceForm.getServiceInternet().getPort());
        }
        return deviceFreePortList;
    }

    @Override
    @Transactional
    public void changeSoftBlockStatus(Integer serviceId, String changedBy){
        serviceDAO.changeSoftBlockStatus(serviceId, changedBy);
    }

    @Transactional
    public List<OnlineStatistic> getUsersOnline() {
        return serviceDAO.getUserOnline();
    }

    @Transactional
    private ServiceForm modifyServiceTypeIfNeed(ServiceForm form, String changerName){
        Integer idService = form.getId();
        ServiceTypeForm serviceType = form.getServiceType();
        if(form.getPeriod() == null && idService != null){
            form = getServiceFormById(idService);
            form.setId(null);
            form.setServiceType(serviceType);
            serviceDAO.deleteService(idService, changerName);
        }
        return form;
    }

    @Transactional
    public List<Integer> listActiveServicesIds() {
        return serviceDAO.listActiveServicesIds();
    }
}
