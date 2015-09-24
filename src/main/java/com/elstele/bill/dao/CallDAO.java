package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Call;

import java.util.List;

public interface CallDAO extends CommonDAO<Call> {
    public List<Call> getCalls();
    public Integer getCallsCount();
    public List<Call> getCallsList(int limit, int offset);
}
