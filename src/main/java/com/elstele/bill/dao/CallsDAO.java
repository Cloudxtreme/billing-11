package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Calls;

import java.util.List;

public interface CallsDAO extends CommonDAO<Calls> {
    public List<Calls> getCalls();
}
