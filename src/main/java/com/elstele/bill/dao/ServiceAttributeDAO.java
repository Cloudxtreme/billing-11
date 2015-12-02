package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.ServiceInternetAttribute;

import java.util.List;


public interface ServiceAttributeDAO extends CommonDAO <ServiceInternetAttribute> {
    public List<ServiceInternetAttribute> getServiceInternetAttributesById(Integer serviceId);
}
