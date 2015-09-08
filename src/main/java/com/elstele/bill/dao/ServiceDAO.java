package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.ServiceT;

import java.util.List;

public interface ServiceDAO extends CommonDAO <ServiceT> {
    public List<ServiceT> listService();
}
