package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.ServiceTypeAssembler;
import com.elstele.bill.dao.interfaces.ServiceAttributeDAO;
import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.dao.interfaces.ServiceTypeDAO;
import com.elstele.bill.datasrv.interfaces.ServiceTypeDataService;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceTypeDataServiceImpl implements ServiceTypeDataService {

    @Autowired
    private ServiceTypeDAO serviceTypeDAO;
    @Autowired
    private ServiceAttributeDAO serviceAttributeDAO;

    @Override
    @Transactional
    public String saveServiceType(ServiceTypeForm form) {
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        String message = "Service was successfully ";
        ServiceType service = assembler.fromFormToBean(form);
        if(form.isNew()){
            serviceTypeDAO.create(service);
            message += "added.";
        }
        else{
            serviceTypeDAO.update(service);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public List<ServiceType> listServiceType(){
        return serviceTypeDAO.listServiceType();
    }

    @Transactional
    public List<ServiceType> listServiceTypeByBussType(Constants.AccountType bussType) {
        return serviceTypeDAO.listServiceTypeByBussType(bussType);
    }

    @Override
    @Transactional
    public void deleteServiceType(Integer id) {
        serviceTypeDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public ServiceTypeForm getServiceTypeFormById(Integer id){
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        ServiceTypeForm result = null;
        ServiceType bean = serviceTypeDAO.getById(id);
        if (bean != null){
            ServiceTypeForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }

    @Override
    @Transactional
    public List<ServiceInternetAttributeForm> listServiceAttribute(Integer serviceId) {
        List<ServiceInternetAttributeForm> result = new ArrayList<ServiceInternetAttributeForm>();
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();

        List<ServiceInternetAttribute> attributeBeans = serviceAttributeDAO.getServiceInternetAttributesById(serviceId);
        if (attributeBeans != null){
            for (ServiceInternetAttribute curAttributeBean : attributeBeans) {
                ServiceInternetAttributeForm curForm = assembler.fromServiceInternetAttributeBeanToForm(curAttributeBean);
                result.add(curForm);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public String saveServiceAttribute(ServiceInternetAttributeForm form){
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        String message = "Service Attribute was successfully ";
        ServiceInternetAttribute serviceAttribute = assembler.fromServiceInternetAttributeFormToBean(form);
        if(form.isNew()){
            serviceAttributeDAO.create(serviceAttribute);
            message += "added.";
        }
        else{
            serviceAttributeDAO.update(serviceAttribute);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public void deleteServiceAttribute(Integer serviceAttributeId){
        serviceAttributeDAO.setStatusDelete(serviceAttributeId);
    }

    @Override
    @Transactional
    public ServiceInternetAttributeForm getServiceAttributeForm(Integer serviceAttributeId, Integer serviceId) {
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        ServiceInternetAttributeForm result = null;
        if (serviceAttributeId > 0){
            ServiceInternetAttribute bean = serviceAttributeDAO.getById(serviceAttributeId);
            if (bean != null) {
                ServiceInternetAttributeForm form = assembler.fromServiceInternetAttributeBeanToForm(bean);
                result = form;
            }
        }
        else{
            result = new ServiceInternetAttributeForm();
            result.setServiceTypeId(serviceId);
        }
        return result;
    }
}
