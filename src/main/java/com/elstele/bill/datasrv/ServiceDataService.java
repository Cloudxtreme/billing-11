package com.elstele.bill.datasrv;

import com.elstele.bill.domain.Service;
import com.elstele.bill.form.ServiceForm;

import java.util.List;

public interface ServiceDataService {

    public String saveService(ServiceForm form);
    public void deleteService(Integer id);
    public ServiceForm getServiceFormById(Integer id);
    public Service getServiceBeanById(Integer id);
    public List<Service> listServices();

}
