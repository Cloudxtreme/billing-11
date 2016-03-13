package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.to.TransactionTO;

import java.util.Date;
import java.util.List;

public interface TransactionDataService {
    public String saveTransaction(TransactionForm form);
    public List<TransactionForm> getTransactionList(Integer accountId);
    public List<TransactionForm> getTransactionList(Integer accountId, Integer displayLimit);
    public List<TransactionForm> searchTransactionList(String account, Date dateStart, Date dateEnd);
    public TransactionForm getTransactionForm(Integer accountId);
    public TransactionForm getTransactionById(Integer transactionId);
    public List<TransactionTO> getTransactionForAccount(Integer accountId);
}
