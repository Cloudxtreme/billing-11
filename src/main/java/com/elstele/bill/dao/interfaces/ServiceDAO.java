package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.OnlineStatistic;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceInternetAttribute;

import java.util.List;

public interface ServiceDAO extends CommonDAO <Service> {
    public List<Service> listServices();
    public String saveService(Service service, boolean isNewObject);
    public void deleteService(Integer serviceId);
    public List<OnlineStatistic> getUserOnline();
    public void changeSoftBlockStatus(Integer serviceId);
}
