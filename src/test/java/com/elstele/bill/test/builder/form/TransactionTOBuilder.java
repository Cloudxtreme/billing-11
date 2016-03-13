package com.elstele.bill.test.builder.form;

import com.elstele.bill.to.TransactionTO;
import com.elstele.bill.utils.Constants;

import java.util.Date;

public class TransactionTOBuilder {
    private TransactionTO transactionTO;

    public TransactionTOBuilder build() {
        transactionTO = new TransactionTO();
        return this;
    }
    public TransactionTO getRes(){
        if (transactionTO == null){
            build();
        }
        return transactionTO;
    }

    public TransactionTOBuilder withId(Integer id){
        transactionTO.setId(id);
        return this;
    }
    public TransactionTOBuilder withDirection(Constants.TransactionDirection direction){
        transactionTO.setDirection(direction);
        return this;
    }
    public TransactionTOBuilder withDate(Date date){
        transactionTO.setDate(date);
        return this;
    }
    public TransactionTOBuilder withSource(Constants.TransactionSource source){
        transactionTO.setSource(source);
        return this;
    }
    public TransactionTOBuilder withPrice(Float price){
        transactionTO.setPrice(price);
        return this;
    }
    public TransactionTOBuilder withComment(String comment){
        transactionTO.setComment(comment);
        return this;
    }
    public TransactionTOBuilder withAccountId(Integer accountId){
        transactionTO.setAccountId(accountId);
        return this;
    }
}
