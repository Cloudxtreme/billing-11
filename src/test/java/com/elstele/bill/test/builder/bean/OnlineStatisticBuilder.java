package com.elstele.bill.test.builder;

import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Address;
import com.elstele.bill.domain.OnlineStatistic;
import com.elstele.bill.test.builder.bean.TestObjectCreator;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;
import org.apache.commons.lang.RandomStringUtils;

import java.math.BigInteger;
import java.util.Random;

public class OnlineStaticticBuilder implements TestObjectCreator<OnlineStaticticBuilder, OnlineStatistic> {
    private OnlineStatistic statistic;

    public OnlineStaticticBuilder build() {
        statistic = new OnlineStatistic();
        return this;
    }

    public OnlineStatistic getRes() {
        if (statistic == null){
            build();
        }
        return statistic;
    }

    public OnlineStaticticBuilder withUsername(String username){
        statistic.setUsername(username);
        return this;
    }

    public OnlineStaticticBuilder withUserfio(String user_fio){
        statistic.setUser_fio(user_fio);
        return this;
    }

    public OnlineStaticticBuilder withNasipaddress(String nasipaddress){
        statistic.setNasipaddress(nasipaddress);
        return this;
    }

    public OnlineStaticticBuilder withNasportid(String nasportid){
        statistic.setNasportid(nasportid);
        return this;
    }

    public OnlineStaticticBuilder withAcctstarttime(String acctstarttime){
        statistic.setAcctstarttime(acctstarttime);
        return this;
    }

    public OnlineStaticticBuilder withFramedipaddress(String framedipaddress){
        statistic.setFramedipaddress(framedipaddress);
        return this;
    }

    public OnlineStaticticBuilder withAcctsessiontime(BigInteger acctsessiontime){
        statistic.setAcctsessiontime(acctsessiontime);
        return this;
    }

    public OnlineStaticticBuilder withAcctinputoctets(BigInteger acctinputoctets){
        statistic.setAcctinputoctets(acctinputoctets);
        return this;
    }

    public OnlineStaticticBuilder withAcctoutputoctets(BigInteger acctoutputoctets){
        statistic.setAcctoutputoctets(acctoutputoctets);
        return this;
    }

    public OnlineStaticticBuilder withAllRandomFields(){
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
