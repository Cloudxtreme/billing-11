package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionDAO extends CommonDAO<Transaction>{
    public List<Transaction> getTransactionList(Integer accountId);
    public List<Transaction> getTransactionList(Integer accountId, Integer displayLimit);
    public List<Transaction> searchTransactionList(String account, Date dateStart, Date dateEnd);
    public void copyBalsnceToHistBalanceForAccount(Integer accountId, Float balance);
}
