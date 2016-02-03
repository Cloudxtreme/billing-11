package com.elstele.bill.datasrv.interfaces;

public interface BillingDataService {
    public Integer createTransactionAndDecreaseBalance(Integer serviceId);
    public void copyCurAccountBalToHistBalance();
}
