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
import com.elstele.bill.utils.Enums.Status;
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
    public String saveServiceType(ServiceTypeForm form, String changerName) {
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        ServiceType transientService = assembler.fromFormToBean(form);
        if (form.isNew()) {
            transientService.setStatus(Status.ACTIVE);
            serviceTypeDAO.create(transientService, changerName);
            return "service.success.add";
        } else {
            serviceTypeDAO.update(transientService, changerName);
            return "service.success.update";
        }
    }

    @Override
    @Transactional
    public boolean checkUniqueTypeName(ServiceTypeForm form) {
        ServiceType type = serviceTypeDAO.getByName(form.getName());
        return type == null || type.getId().equals(form.getId()) && type.getName().equals(form.getName());
    }

    @Override
    @Transactional
    public List<ServiceType> listServiceType() {
        return serviceTypeDAO.listServiceType();
    }

    @Override
    @Transactional
    public List<ServiceType> listServiceType(String type) {
        return serviceTypeDAO.listServiceType(type);
    }

    @Transactional
    public List<ServiceType> listServiceTypeByBussType(Constants.AccountType bussType) {
        return serviceTypeDAO.listServiceTypeByBussType(bussType);
    }

    @Override
    @Transactional
    public void deleteServiceType(Integer id, String changerName) {
        ServiceType serviceType = serviceTypeDAO.getById(id);
        serviceType.setStatus(Status.DELETED);
        serviceTypeDAO.update(serviceType, changerName);
    }

    @Override
    @Transactional
    public ServiceTypeForm getServiceTypeFormById(Integer id) {
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        ServiceTypeForm result = null;
        ServiceType bean = serviceTypeDAO.getById(id);
        if (bean != null) {
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
        if (attributeBeans != null) {
            for (ServiceInternetAttribute curAttributeBean : attributeBeans) {
                ServiceInternetAttributeForm curForm = assembler.fromServiceInternetAttributeBeanToForm(curAttributeBean);
                result.add(curForm);
            }
        }
        return result;
    }

    @Override
    @Transactional
    public String saveServiceAttribute(ServiceInternetAttributeForm form) {
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        ServiceInternetAttribute serviceAttribute = assembler.fromServiceInternetAttributeFormToBean(form);
        if (form.isNew()) {
            serviceAttributeDAO.create(serviceAttribute);
            return "serviceAttr.success.add";
        } else {
            serviceAttributeDAO.update(serviceAttribute);
            return "serviceAttr.success.update";
        }
    }

    @Override
    @Transactional
    public void deleteServiceAttribute(Integer serviceAttributeId) {
        serviceAttributeDAO.setStatusDelete(serviceAttributeId);
    }

    @Override
    @Transactional
    public ServiceInternetAttributeForm getServiceAttributeForm(Integer serviceAttributeId, Integer serviceId) {
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        ServiceInternetAttributeForm result = null;
        if (serviceAttributeId > 0) {
            ServiceInternetAttribute bean = serviceAttributeDAO.getById(serviceAttributeId);
            if (bean != null) {
                ServiceInternetAttributeForm form = assembler.fromServiceInternetAttributeBeanToForm(bean);
                result = form;
            }
        } else {
            result = new ServiceInternetAttributeForm();
            result.setServiceTypeId(serviceId);
        }
        return result;
    }
}
