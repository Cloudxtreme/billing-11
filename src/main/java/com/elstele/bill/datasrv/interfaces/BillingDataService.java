package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.TransactionForm;

import java.util.List;

public interface BillingDataService {
    public Integer createTransactionAndDecreaseBalance(Integer serviceId);
}
