package com.elstele.bill.datasrv;

import com.elstele.bill.domain.Service;
import com.elstele.bill.form.ServiceForm;

import java.util.List;

public interface ServiceDataService {

    public String saveService(ServiceForm form);
    public void deleteService(Integer id);
    public ServiceForm getServiceFormById(Integer serviceId);
    public Integer getCurrentIpAddress (ServiceForm serviceForm);
    public Integer getCurrentIpAddressByServiceFormId(Integer serviceFormId);
    public List<Integer> addCurrentDevicePortToList(List<Integer> deviceFreePortList, Integer serviceId, Integer deviceId);
    public Service getServiceBeanById(Integer id);
    public List<Service> listServices();

}
