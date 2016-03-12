package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.utils.Constants;

import java.util.List;

public interface ServiceTypeDAO extends CommonDAO <ServiceType> {
    public List<ServiceType> listServiceType();
    public List<ServiceType> listServiceType(String type, Constants.AccountType bussType);
    public List<ServiceType> listServiceTypeByBussType(Constants.AccountType bussTyp);
    public ServiceType getByName(String name);

    public Integer create(ServiceType serviceType, String changerName);
    public void update(ServiceType serviceType, String changerName);
}
