package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.TransactionAssembler;
import com.elstele.bill.dao.TransactionDAO;
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
        List<TransactionForm> result = new ArrayList<TransactionForm>();
        TransactionAssembler assembler = new TransactionAssembler();

        List<Transaction> beans = transactionDAO.getTransactionList(accountId);
        for (Transaction curBean : beans){
            TransactionForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
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

}
