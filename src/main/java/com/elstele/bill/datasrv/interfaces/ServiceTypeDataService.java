package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;

import java.util.List;

public interface ServiceTypeDataService {

    public String saveServiceType(ServiceTypeForm form);
    public void deleteServiceType(Integer id);
    public List<ServiceType> listServiceType();
    public List<ServiceType> listServiceType(String type);
    public List<ServiceType> listServiceTypeByBussType(Constants.AccountType bussType);
    public ServiceTypeForm getServiceTypeFormById(Integer id);
    public List<ServiceInternetAttributeForm> listServiceAttribute(Integer serviceId);
    public String saveServiceAttribute(ServiceInternetAttributeForm form);
    public void deleteServiceAttribute(Integer serviceAttributeId);
    public ServiceInternetAttributeForm getServiceAttributeForm(Integer serviceAttributeId, Integer serviceId);
    public boolean checkUniqueTypeName(ServiceTypeForm form);
}
