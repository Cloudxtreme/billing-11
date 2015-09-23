package com.elstele.bill.datasrv;

import com.elstele.bill.domain.ServiceT;
import com.elstele.bill.form.ServiceForm;

import java.util.List;

public interface ServiceDataService {

    public String saveService(ServiceForm form);
    public void deleteService(Integer id);
    public List<ServiceT> listService();
    public ServiceForm getServiceFormById(Integer id);

}
