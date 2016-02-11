package com.elstele.bill.Builders.bean;
import com.elstele.bill.reportCreators.CallTO;

import java.math.BigInteger;
import java.util.Date;

public class CallTOBuilder implements TestObjectCreator<CallTOBuilder, CallTO> {
    private CallTO callTO;

    @Override
    public CallTOBuilder build() {
        callTO = new CallTO();
        return this;
    }

    public CallTOBuilder withNumberB(String numberb){
        callTO.setNumberb(numberb);
        return this;
    }

    public CallTOBuilder withStartTime(Date date){
        callTO.setStarttime(date);
        return this;
    }

    public CallTOBuilder withDuration(BigInteger duration){
        callTO.setDuration(duration);
        return this;
    }

    public CallTOBuilder withCostTotal(Float costtotal){
        callTO.setCosttotal(costtotal);
        return this;
    }

    public CallTOBuilder withDescription(String description){
        callTO.setDescription(description);
        return this;
    }

    public CallTOBuilder withPrefix(String prefix){
        callTO.setPrefix(prefix);
        return this;
    }

    @Override
    public CallTO getRes() {
        if(callTO == null){
            build();
        }
        return callTO;
    }
}
