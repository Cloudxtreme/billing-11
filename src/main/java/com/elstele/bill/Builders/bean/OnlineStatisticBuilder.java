package com.elstele.bill.Builders.bean;

import com.elstele.bill.domain.OnlineStatistic;
import org.apache.commons.lang.RandomStringUtils;

import java.math.BigInteger;
import java.util.Random;

public class OnlineStatisticBuilder implements TestObjectCreator<OnlineStatisticBuilder, OnlineStatistic> {
    private OnlineStatistic statistic;

    public OnlineStatisticBuilder build() {
        statistic = new OnlineStatistic();
        return this;
    }

    public OnlineStatistic getRes() {
        if (statistic == null){
            build();
        }
        return statistic;
    }

    public OnlineStatisticBuilder withUsername(String username){
        statistic.setUsername(username);
        return this;
    }

    public OnlineStatisticBuilder withUserfio(String user_fio){
        statistic.setUser_fio(user_fio);
        return this;
    }

    public OnlineStatisticBuilder withNasipaddress(String nasipaddress){
        statistic.setNasipaddress(nasipaddress);
        return this;
    }

    public OnlineStatisticBuilder withNasportid(String nasportid){
        statistic.setNasportid(nasportid);
        return this;
    }

    public OnlineStatisticBuilder withAcctstarttime(String acctstarttime){
        statistic.setAcctstarttime(acctstarttime);
        return this;
    }

    public OnlineStatisticBuilder withFramedipaddress(String framedipaddress){
        statistic.setFramedipaddress(framedipaddress);
        return this;
    }

    public OnlineStatisticBuilder withAcctsessiontime(BigInteger acctsessiontime){
        statistic.setAcctsessiontime(acctsessiontime);
        return this;
    }

    public OnlineStatisticBuilder withAcctinputoctets(BigInteger acctinputoctets){
        statistic.setAcctinputoctets(acctinputoctets);
        return this;
    }

    public OnlineStatisticBuilder withAcctoutputoctets(BigInteger acctoutputoctets){
        statistic.setAcctoutputoctets(acctoutputoctets);
        return this;
    }

    public OnlineStatisticBuilder withAllRandomFields(){
        statistic.setUsername(RandomStringUtils.randomAlphanumeric(8));
        statistic.setUser_fio(RandomStringUtils.randomAlphabetic(20));
        statistic.setNasipaddress(RandomStringUtils.randomNumeric(16));
        statistic.setNasportid(RandomStringUtils.randomNumeric(3));
        statistic.setFramedipaddress(RandomStringUtils.randomNumeric(16));
        statistic.setAcctstarttime(RandomStringUtils.randomAlphanumeric(10));
        statistic.setAcctsessiontime(nextRandomBigInteger());
        statistic.setAcctinputoctets(nextRandomBigInteger());
        statistic.setAcctoutputoctets(nextRandomBigInteger());

        return this;
    }


    public BigInteger nextRandomBigInteger() {
        Random rand = new Random();
        BigInteger result = new BigInteger(6, rand);
        return result;
    }
}