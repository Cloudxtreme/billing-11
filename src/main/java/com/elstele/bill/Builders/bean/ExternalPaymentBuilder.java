package com.elstele.bill.Builders.bean;

import com.elstele.bill.domain.ExternalPaymentTransaction;
import com.elstele.bill.utils.Enums.Status;

import java.util.Date;

public class ExternalPaymentBuilder {
    private ExternalPaymentTransaction transaction;

    public ExternalPaymentBuilder build(){
        transaction = new ExternalPaymentTransaction();
        return this;
    }

    public ExternalPaymentBuilder withId(int val){
        transaction.setId(val);
        return this;
    }
    public ExternalPaymentBuilder withStatus(Status val){
        transaction.setStatus(val);
        return this;
    }
    public ExternalPaymentBuilder withServiceId(String val){
        transaction.setServiceId(val);
        return this;
    }
    public ExternalPaymentBuilder withPayAccount(String val){
        transaction.setPayAccount(val);
        return this;
    }
    public ExternalPaymentBuilder withPayAmount(float val){
        transaction.setPayAmount(val);
        return this;
    }
    public ExternalPaymentBuilder withReceiptNum(String val){
        transaction.setReceiptNum(val);
        return this;
    }
    public ExternalPaymentBuilder withPayId(String val){
        transaction.setPayId(val);
        return this;
    }
    public ExternalPaymentBuilder withTradePoint(String val){
        transaction.setTradepoint(val);
        return this;
    }
    public ExternalPaymentBuilder withTimestamp(Date val){
        transaction.setTimestamp(val);
        return this;
    }
    public ExternalPaymentBuilder withCheck(boolean val){
        transaction.setCheck(val);
        return this;
    }

    public ExternalPaymentTransaction getRes(){
        if(transaction == null){
            build();
        }
        return transaction;
    }


}
