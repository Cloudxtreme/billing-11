package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.TransactionForm;

import java.util.List;

public interface TransactionDataService {
    public String saveTransaction(TransactionForm form);
    public List<TransactionForm> getTransactionList(Integer accountId);
    public List<TransactionForm> getTransactionList(Integer accountId, Integer displayLimit);
    public TransactionForm getTransactionForm(Integer accountId);
    public TransactionForm getTransactionById(Integer transactionId);
}
