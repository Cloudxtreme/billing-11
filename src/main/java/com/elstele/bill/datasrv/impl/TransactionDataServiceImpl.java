package com.elstele.bill.datasrv.impl;

import com.elstele.bill.assembler.TransactionAssembler;
import com.elstele.bill.dao.interfaces.TransactionDAO;
import com.elstele.bill.datasrv.interfaces.TransactionDataService;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionDataServiceImpl implements TransactionDataService {
    @Autowired
    private TransactionDAO transactionDAO;

    @Override
    @Transactional
    public List<TransactionForm> getTransactionList(Integer accountId){
        List<Transaction> beans = transactionDAO.getTransactionList(accountId);
        return makeTransactionFormList(beans);
    }

    @Override
    @Transactional
    public List<TransactionForm> getTransactionList(Integer accountId, Integer displayLimit){
        List<Transaction> beans = transactionDAO.getTransactionList(accountId, displayLimit);
        return makeTransactionFormList(beans);
    }

    @Override
    @Transactional
    public String saveTransaction(TransactionForm transactionForm) {
        TransactionAssembler assembler = new TransactionAssembler();
        Transaction transaction = assembler.fromFormToBean(transactionForm);
        transactionDAO.create(transaction);
        String message = "Transaction was successfully created";
        return message;
    }

    @Override
    public TransactionForm getTransactionForm(Integer accountId){
        TransactionForm transactionForm = new TransactionForm();
        if(accountId > 0) {
            AccountForm accountForm = new AccountForm();
            accountForm.setId(accountId);
            transactionForm.setAccount(accountForm);
        }
        return transactionForm;
    }

    private List<TransactionForm> makeTransactionFormList(List<Transaction> beans){
        List<TransactionForm> result = new ArrayList<TransactionForm>();
        TransactionAssembler assembler = new TransactionAssembler();
        for (Transaction curBean : beans){
            TransactionForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }
}
