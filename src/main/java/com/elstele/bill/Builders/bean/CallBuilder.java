package com.elstele.bill.Builders.bean;

import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.Enums.Status;
import org.apache.commons.lang.RandomStringUtils;

import java.util.Date;

public class CallBuilder implements TestObjectCreator<CallBuilder, Call> {
    private Call call;

    @Override
    public CallBuilder build() {
        call = new Call();
        call.setStatus(Status.ACTIVE);
        return this;
    }

    public CallBuilder withId(Integer id){
        call.setId(id);
        return this;
    }

    public CallBuilder withNumberA(String numberA){
        call.setNumberA(numberA);
        return this;
    }

    public CallBuilder withNumberB(String numberB){
        call.setNumberB(numberB);
        return this;
    }

    public CallBuilder randomNumberA(){
        call.setNumberA(RandomStringUtils.randomAlphanumeric(7));
        return this;
    }

    public CallBuilder randomNumberB(){
        call.setNumberB(RandomStringUtils.randomAlphanumeric(10));
        return this;
    }

    public CallBuilder withStartTime(Date date){
        call.setStartTime(date);
        return this;
    }

    public CallBuilder withDuration(int duration){
        call.setDuration(duration);
        return this;
    }

    public CallBuilder withAonKat(String aonKat){
        call.setAonKat(aonKat);
        return this;
    }

    public CallBuilder withDvoCodeA(String dvoCodeA){
        call.setDvoCodeA(dvoCodeA);
        return this;
    }

    public CallBuilder withDvoCodeB(String dvoCodeB){
        call.setDvoCodeB(dvoCodeB);
        return this;
    }

    public CallBuilder withIkNum(String ikNum){
        call.setIkNum(ikNum);
        return this;
    }

    public CallBuilder withVkNum(String vkNum){
        call.setVkNum(vkNum);
        return this;
    }

    public CallBuilder withInputTrunk(String inputTrunk){
        call.setInputTrunk(inputTrunk);
        return this;
    }

    public CallBuilder withOutputTrunk(String outputTrunk){
        call.setOutputTrunk(outputTrunk);
        return this;
    }

    public CallBuilder withSecRegular(Integer secRegular){
        call.setSecRegular(secRegular);
        return this;
    }

    public CallBuilder withSecPref(Integer secPref){
        call.setSecPref(secPref);
        return this;
    }

    public CallBuilder withCostRegular(Float costRegular){
        call.setCostRegular(costRegular);
        return this;
    }

    public CallBuilder withCostPref(Float costPref){
        call.setCostPref(costPref);
        return this;
    }

    public CallBuilder withCostTotal(Float costTotal){
        call.setCostTotal(costTotal);
        return this;
    }

    public CallBuilder withCallDirectionId(Integer callDirectionId){
        call.setCallDirectionId(callDirectionId);
        return this;
    }

    @Override
    public Call getRes() {
        if (call == null){
            this.build();
        }
        return call;
    }
}
