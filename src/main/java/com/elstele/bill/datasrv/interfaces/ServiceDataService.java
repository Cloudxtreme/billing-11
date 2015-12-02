package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.Service;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceInternetAttributeForm;

import java.util.List;

public interface ServiceDataService {

    public String saveService(ServiceForm form);
    public void deleteService(Integer id);
    public ServiceForm getServiceFormById(Integer serviceId);
    public Integer getCurrentIpAddress (ServiceForm serviceForm);
    public Integer getCurrentIpAddressByServiceFormId(Integer serviceFormId);
    public List<Integer> addCurrentDevicePortToList(List<Integer> deviceFreePortList, Integer serviceId, Integer deviceId);
    public List<Service> listServices();

}
