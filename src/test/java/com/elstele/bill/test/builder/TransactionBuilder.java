package com.elstele.bill.test.builder;

import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Transaction;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.apache.commons.lang.RandomStringUtils;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

public class TransactionBuilder {
    private Transaction transaction;
    Random random = new Random();

    public TransactionBuilder build() {
        transaction = new Transaction();
        transaction.setStatus(Status.ACTIVE);
        return this;
    }
    public Transaction getRes(){
        if (transaction == null){
            build();
        }
        return transaction;
    }

    public TransactionBuilder randomTransaction(){
        Constants.TransactionDirection directionRandom = Constants.TransactionDirection.values()[(int) (Math.random() * Constants.TransactionDirection.values().length)];
        Constants.TransactionSource sourceRandom = Constants.TransactionSource.values()[(int) (Math.random() * Constants.TransactionSource.values().length)];
        transaction.setDirection(directionRandom);
        transaction.setDate(getTimestamp());
        transaction.setSource(sourceRandom);
        transaction.setPrice(random.nextFloat());
        transaction.setComment(RandomStringUtils.randomAlphanumeric(8));
        AccountBuilder ab = new AccountBuilder();
        Account account = ab.build().withAccName("ACC_001").withAccType(Constants.AccountType.PRIVATE).withBalance(20f).withRandomPhyAddress().getRes();
        transaction.setAccount(account);
        return this;
    }

    public TransactionBuilder withDirection(Constants.TransactionDirection direction){
        transaction.setDirection(direction);
        return this;
    }
    public TransactionBuilder withDate(Date date){
        transaction.setDate(date);
        return this;
    }
    public TransactionBuilder withSource(Constants.TransactionSource source){
        transaction.setSource(source);
        return this;
    }
    public TransactionBuilder withPrice(Float price){
        transaction.setPrice(price);
        return this;
    }
    public TransactionBuilder withComment(String comment){
        transaction.setComment(comment);
        return this;
    }
    public TransactionBuilder withAccount(Account account){
        transaction.setAccount(account);
        return this;
    }

    private Timestamp getTimestamp(){
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp;
    }
}
