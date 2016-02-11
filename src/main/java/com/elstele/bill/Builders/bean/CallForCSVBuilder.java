package com.elstele.bill.Builders.bean;

import com.elstele.bill.domain.CallForCSV;

import java.util.Date;

public class CallForCSVBuilder implements TestObjectCreator<CallForCSVBuilder, CallForCSV> {
    private CallForCSV callForCSV;

    @Override
    public CallForCSVBuilder build() {
        callForCSV = new CallForCSV();
        return this;
    }

    public CallForCSVBuilder withId(Integer id){
        callForCSV.setId(id);
        return this;
    }

    public CallForCSVBuilder withNumberA(String numberA){
        callForCSV.setNumberA(numberA);
        return this;
    }

    public CallForCSVBuilder withNumberB(String numberB){
        callForCSV.setNumberB(numberB);
        return this;
    }

    public CallForCSVBuilder withDuration(Integer duration){
        callForCSV.setDuration(duration);
        return this;
    }

    public CallForCSVBuilder withStartTime(Date startTime){
        callForCSV.setStartTime(startTime);
        return this;
    }

    public CallForCSVBuilder withDirPrefix(String dirPrefix){
        callForCSV.setDirPrefix(dirPrefix);
        return this;
    }

    public CallForCSVBuilder withDirDescrpOrg(String dirDescrpOrg){
        callForCSV.setDirDescrpOrg(dirDescrpOrg);
        return this;
    }

    public CallForCSVBuilder withCostCallTotal(String costCallTotal){
        callForCSV.setCostCallTotal(costCallTotal);
        return this;
    }

    public CallForCSVBuilder withProvider(String provider){
        callForCSV.setProvider(provider);
        return this;
    }

    @Override
    public CallForCSV getRes() {
        if(callForCSV == null){
            this.build();

        }
        return callForCSV;
    }
}
