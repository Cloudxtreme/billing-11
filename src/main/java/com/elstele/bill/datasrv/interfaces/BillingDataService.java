package com.elstele.bill.datasrv.interfaces;

public interface BillingDataService {
    Integer createTransactionAndDecreaseBalance(Integer serviceId);
    void copyCurAccountBalToHistBalance();
}
