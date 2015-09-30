package com.elstele.bill.datasrv;


import com.elstele.bill.form.CallForm;
import com.elstele.bill.utils.TempObjectForCallsRequestParam;

import java.util.Date;
import java.util.List;

public interface CallDataService {
    public void addCalls(CallForm callForm);
    public int getCallsCount();
    public int getCallsCountWithSearchValues(TempObjectForCallsRequestParam tempObjectForCallsRequestParam);
    public List<CallForm> getCallsList(int rows, int page);
    public List<CallForm> callsListSelectionBySearch(int limit, int offset, String numberA, String numberB, Date startDate, Date endDate);

}
