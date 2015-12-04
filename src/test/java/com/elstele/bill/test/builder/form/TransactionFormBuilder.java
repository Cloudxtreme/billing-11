package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.utils.Constants;

import java.util.Date;

public class TransactionFormBuilder {
    private TransactionForm transactionForm;

    public TransactionFormBuilder build() {
        transactionForm = new TransactionForm();
        return this;
    }
    public TransactionForm getRes(){
        if (transactionForm == null){
            build();
        }
        return transactionForm;
    }

    public TransactionFormBuilder withDirection(Constants.TransactionDirection direction){
        transactionForm.setDirection(direction);
        return this;
    }
    public TransactionFormBuilder withDate(Date date){
        transactionForm.setDate(date);
        return this;
    }
    public TransactionFormBuilder withSource(Constants.TransactionSource source){
        transactionForm.setSource(source);
        return this;
    }
    public TransactionFormBuilder withPrice(Float price){
        transactionForm.setPrice(price);
        return this;
    }
    public TransactionFormBuilder withComment(String comment){
        transactionForm.setComment(comment);
        return this;
    }
    public TransactionFormBuilder withAccount(AccountForm accountForm){
        transactionForm.setAccount(accountForm);
        return this;
    }
}
