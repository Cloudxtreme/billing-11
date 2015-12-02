package com.elstele.bill.datasrv.interfaces;


import com.elstele.bill.domain.Call;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.reportCreators.CallTO;
import com.elstele.bill.reportCreators.CallsRequestParamTO;

import java.util.Date;
import java.util.List;

public interface CallDataService {
    public void addCalls(CallForm callForm);
    public int getCallsCount();
    public int getCallsCountWithSearchValues(CallsRequestParamTO callsRequestParamTO);
    public List<CallForm> getCallsList(int rows, int page);
    public List<CallForm> callsListSelectionBySearch(int limit, int offset, String numberA, String numberB, Date startDate, Date endDate);
    public List<String> getUniqueNumberAFromCalls(Date startTime, Date finishTime);
    public List<CallTO> getCallByNumberA(String numberA, Date startTime, Date endTime);
    public List<String> getUniqueNumberAFromCallsWithTrunk(Date startTime, Date finishTime, String outputTrunk);
    public List<CallTO> getCallByNumberAWithTrunk(String numberA, Date startTime, Date finishTime, String outputTrunk);
    public List<String> getUniqueLocalNumberAFromCalls(Date startTime, Date finishTime);
    public List<Call> getLocalCalls(String numberA, Date startTime, Date endTime);
    public List<String> getYearsList();

    public Integer getUnbilledCallsCount();
    public List<Integer> getUnbilledCallsIdList(int limit, int offset);

}
