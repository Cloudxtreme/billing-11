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
    public void deleteService(Integer id) {
        serviceDAO.deleteService(id);
    }

    @Override
    @Transactional
    public List<Service> listServices() {
        return serviceDAO.listServices();
    }


    @Override
    @Transactional
    public String saveService(ServiceForm form) {
        Service service = serviceAssembler.getServiceBeanByForm(form);
        return serviceDAO.saveService(service, form.isNew());
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
            ServiceForm serviseForm = getServiceFormById(serviceId);
            if( deviceId.equals(serviseForm.getServiceInternet().getDevice().getId()) )
                deviceFreePortList.add(0,serviseForm.getServiceInternet().getPort());
        }
        return deviceFreePortList;
    }

    @Transactional
    public List<OnlineStatistic> getUsersOnline() {
        return serviceDAO.getUserOnline();
    }
}
