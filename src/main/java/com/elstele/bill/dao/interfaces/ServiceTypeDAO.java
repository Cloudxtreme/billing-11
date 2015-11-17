package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.ServiceType;

import java.util.List;

public interface ServiceTypeDAO extends CommonDAO <ServiceType> {
    public List<ServiceType> listServiceType();
}
