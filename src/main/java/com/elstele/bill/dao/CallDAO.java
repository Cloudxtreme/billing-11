package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.TempObjectForCallsRequestParam;

import java.util.Date;
import java.util.List;

public interface CallDAO extends CommonDAO<Call> {
    public List<Call> getCalls();
    public Integer getCallsCount();
    public Integer getCallsCountWithSearchValues(TempObjectForCallsRequestParam tempObjectForCallsRequestParam);
    public List<Call> getCallsList(int limit, int offset);
    public List<Call> callsListSelectionBySearch(int limit, int offset, String numberA, String numberB, Date startDate, Date endDate);
}
