package com.elstele.bill.datasrv;

import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceTypeForm;

import java.util.List;

public interface ServiceTypeDataService {

    public String saveServiceType(ServiceTypeForm form);
    public void deleteServiceType(Integer id);
    public List<ServiceType> listServiceType();
    public ServiceTypeForm getServiceTypeFormById(Integer id);

}
