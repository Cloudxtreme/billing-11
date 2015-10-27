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
    public List<String> getUniqueNumberAFromCalls(Date startTime, Date finishTime);
    public List<Call> getCallByNumberA(String numberA, Date startTime, Date endTime);
    public List<String> getUniqueNumberAFromCallsWithTrunk(Date startTime, Date finishTime, String outputTrunk);
    public List<Call> getCallByNumberAWithTrunk(String numberA, Date startTime, Date finishTime, String outputTrunk);
    public List<String> getUniqueLocalNumberAFromCalls(Date startTime, Date finishTime);
    public List<Call> getLocalCalls(String numberA, Date startTime, Date endTime);
    public Integer getUnbilledCallsCount();
    public List<Integer> getUnbilledCallIds(int limit, int offset);
}
