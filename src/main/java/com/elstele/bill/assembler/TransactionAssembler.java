package com.elstele.bill.assembler;

import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import java.sql.Timestamp;
import java.util.Date;

import static org.springframework.beans.BeanUtils.copyProperties;

public class TransactionAssembler {
    String[] propsToSkip = {"account"};

    public TransactionForm fromBeanToForm(Transaction bean){
        TransactionForm form = new TransactionForm();
        copyProperties(bean, form, propsToSkip);
        form.setAccount(accountAssembleFromBeanToForm(bean.getAccount()));
        return form;
    }

    private AccountForm accountAssembleFromBeanToForm(Account bean) {
        AccountAssembler accountAssembler = new AccountAssembler();
        AccountForm form = accountAssembler.fromBeanToForm(bean);
        return form;
    }

    public Transaction fromFormToBean(TransactionForm form){
        Transaction bean = new Transaction();
        copyProperties(form, bean, propsToSkip);

        Account account = new Account();
        account.setId(form.getAccount().getId());
        bean.setAccount(account);

        java.util.Date date = new java.util.Date();
        bean.setDate(new Timestamp(date.getTime()));
        return bean;
    }
}
