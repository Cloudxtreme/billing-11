package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectBuilder {
    public ServiceType createServiceType(Integer id, Float price, String name, String description){
        ServiceType serviceType = new ServiceType();
        serviceType.setId(id);
        serviceType.setPrice(price);
        serviceType.setName(name);
        serviceType.setDescription(description);
        serviceType.setStatus(Status.ACTIVE);
        return serviceType;
    }

    public Account createAccount(Integer id, String name, Float balance, Constants.AccountType acType){
        Account account = new Account();
        account.setId(id);
        account.setAccountName(name);
        account.setCurrentBalance(balance);
        account.setAccountType(acType);
        account.setStatus(Status.ACTIVE);
        return account;
    }
    public AccountForm createAccountForm(Integer id, String name, Float balance, Constants.AccountType acType){
        AccountForm af = new AccountForm();
        af.setId(id);
        af.setAccountName(name);
        af.setCurrentBalance(balance);
        af.setAccountType(acType);
        af.setStatus(Status.ACTIVE);
        return af;
    }

    public Transaction createTransaction(Integer id, Constants.TransactionDirection direction, Float price, Constants.TransactionSource source, Account account){
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setDate(getTimestamp());
        transaction.setDirection(direction);
        transaction.setPrice(price);
        transaction.setSource(source);
        transaction.setAccount(account);
        transaction.setStatus(Status.ACTIVE);
        return transaction;
    }
    public TransactionForm createTransactionForm(Integer id, Constants.TransactionDirection direction, Float price, Constants.TransactionSource source, AccountForm accountForm){
        TransactionForm transaction = new TransactionForm();
        transaction.setId(id);
        transaction.setDate(getTimestamp());
        transaction.setDirection(direction);
        transaction.setPrice(price);
        transaction.setSource(source);
        transaction.setAccount(accountForm);
        return transaction;
    }

    private Timestamp getTimestamp(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
