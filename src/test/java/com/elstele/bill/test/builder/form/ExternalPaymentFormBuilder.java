package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.ExternalPaymentForm;

import java.util.Date;

public class ExternalPaymentFormBuilder {
    private ExternalPaymentForm transaction;

    public ExternalPaymentFormBuilder build(){
        transaction = new ExternalPaymentForm();
        return this;
    }

    public ExternalPaymentFormBuilder withId(int val){
        transaction.setId(val);
        return this;
    }
    public ExternalPaymentFormBuilder withServiceId(String val){
        transaction.setServiceId(val);
        return this;
    }
    public ExternalPaymentFormBuilder withPayAccount(String val){
        transaction.setPayAccount(val);
        return this;
    }
    public ExternalPaymentFormBuilder withPayAmount(float val){
        transaction.setPayAmount(val);
        return this;
    }
    public ExternalPaymentFormBuilder withReceiptNum(String val){
        transaction.setReceiptNum(val);
        return this;
    }
    public ExternalPaymentFormBuilder withPayId(String val){
        transaction.setPayId(val);
        return this;
    }
    public ExternalPaymentFormBuilder withTradePoint(String val){
        transaction.setTradepoint(val);
        return this;
    }
    public ExternalPaymentFormBuilder withTimestamp(Date val){
        transaction.setTimestamp(val);
        return this;
    }
    public ExternalPaymentFormBuilder withCheck(boolean val){
        transaction.setCheck(val);
        return this;
    }

    public ExternalPaymentForm getRes(){
        if(transaction == null){
            build();
        }
        return transaction;
    }

}
