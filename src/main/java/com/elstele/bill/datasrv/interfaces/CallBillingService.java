package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.exceptions.DirectionCallException;

public interface CallBillingService {

    public void updateCallWithItCost(Integer callId) throws DirectionCallException;

}
