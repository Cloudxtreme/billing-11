package com.elstele.bill.dao;


import com.elstele.bill.billparts.CallBillRule;
import com.elstele.bill.billparts.CallDirection;
import com.elstele.bill.domain.Call;

import java.util.Date;
import java.util.List;

public interface CallBillingDAO {
    public CallDirection getCallDirection(String numberB);
    public List<CallBillRule> getCallBillingRule(int billingProfile);
    public float getUsdRateForCall(Date date);

}
