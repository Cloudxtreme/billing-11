package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.OnlineStatistic;
import com.elstele.bill.domain.Service;

import java.util.List;

public interface ServiceDAO extends CommonDAO <Service> {
    public List<Service> listServices();
    public String saveService(Service service, boolean isNewObject, String changerName);
    public void deleteService(Integer serviceId, String changerName);
    public List<OnlineStatistic> getUserOnline();
    public void changeSoftBlockStatus(Integer serviceId, String changedBy);
    public List<Integer> listActiveServicesIds();
    public List<Service> getServiceByLogin(String value);
    public List<Service> getServiceByPhone(String value);
}
