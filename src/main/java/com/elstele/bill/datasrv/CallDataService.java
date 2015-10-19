package com.elstele.bill.datasrv;


import com.elstele.bill.form.CallForm;

import java.util.List;

public interface CallDataService {
    public void addCalls(CallForm callForm);
    public int getCallsCount();
    public List<CallForm> getCallsList(int rows, int page);
    public Integer getUnbilledCallsCount();
    public List<Integer> getUnbilledCallsIdList(int limit, int offset);
}
