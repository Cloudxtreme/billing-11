package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Service;

import java.util.List;

public interface ServiceDAO extends CommonDAO <Service> {
    public List<Service> listServices();
    public String saveService(Service service, boolean isNewObject);
    public void deleteService(Integer serviceId);
}
