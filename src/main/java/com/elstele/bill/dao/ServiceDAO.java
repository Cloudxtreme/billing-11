package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Service;

import java.util.List;

public interface ServiceDAO extends CommonDAO <Service> {
    public List<Service> listServices();
}
