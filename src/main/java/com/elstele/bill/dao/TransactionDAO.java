package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Transaction;

import java.util.List;

public interface TransactionDAO extends CommonDAO<Transaction>{
    public List<Transaction> getTransactionList(Integer accountId);
}
