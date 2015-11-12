package com.elstele.bill.datasrv;

import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.TransactionForm;

import java.util.List;

public interface TransactionDataService {
    public String saveTransaction(TransactionForm form);
    public List<TransactionForm> getTransactionList(Integer accountId);
    public TransactionForm getTransactionForm(Integer accountId);
}
