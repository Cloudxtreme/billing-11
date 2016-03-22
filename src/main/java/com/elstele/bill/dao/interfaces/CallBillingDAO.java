package com.elstele.bill.dao.interfaces;


import com.elstele.bill.billparts.CallBillRule;
import com.elstele.bill.billparts.CallDirection;
import com.elstele.bill.domain.Call;

import java.util.Date;
import java.util.List;

public interface CallBillingDAO {
    public CallDirection getCallDirection(String numberB, Date callDate);
    public List<CallBillRule> getCallBillingRule(int billingProfile, Date callDate);
    public float getUsdRateForCall(Date date);

}
