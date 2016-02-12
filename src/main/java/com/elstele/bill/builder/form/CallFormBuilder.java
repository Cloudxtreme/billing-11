package com.elstele.bill.builder.form;

import com.elstele.bill.form.CallForm;
import com.elstele.bill.utils.Enums.Status;

import java.util.Date;

public class CallFormBuilder {
    private CallForm callForm;

    public CallFormBuilder build(){
        callForm = new CallForm();
        callForm.setStatus(Status.ACTIVE);
        return this;
    }

    public CallFormBuilder withId(Integer id){
        callForm.setId(id);
        return this;
    }

    public CallFormBuilder withNumberA(String numberA){
        callForm.setNumberA(numberA);
        return this;
    }

    public CallFormBuilder withNumberB(String numberB){
        callForm.setNumberB(numberB);
        return this;
    }

    public CallFormBuilder withStartTime(Date date){
        callForm.setStartTime(date);
        return this;
    }

    public CallFormBuilder withDuration(int duration){
        callForm.setDuration(duration);
        return this;
    }

    public CallFormBuilder withAonKat(String aonKat){
        callForm.setAonKat(aonKat);
        return this;
    }

    public CallFormBuilder withDvoCodeA(String dvoCodeA){
        callForm.setDvoCodeA(dvoCodeA);
        return this;
    }

    public CallFormBuilder withDvoCodeB(String dvoCodeB){
        callForm.setDvoCodeB(dvoCodeB);
        return this;
    }

    public CallFormBuilder withIkNum(String ikNum){
        callForm.setIkNum(ikNum);
        return this;
    }

    public CallFormBuilder withVkNum(String vkNum){
        callForm.setVkNum(vkNum);
        return this;
    }

    public CallFormBuilder withInputTrunk(String inputTrunk){
        callForm.setInputTrunk(inputTrunk);
        return this;
    }

    public CallFormBuilder withOutputTrunk(String outputTrunk){
        callForm.setOutputTrunk(outputTrunk);
        return this;
    }

    public CallFormBuilder withCostRegular(Float costRegular){
        callForm.setCostRegular(costRegular);
        return this;
    }

    public CallFormBuilder withCostPref(Float costPref){
        callForm.setCostPref(costPref);
        return this;
    }

    public CallFormBuilder withCostTotal(Float costTotal){
        callForm.setCostTotal(costTotal);
        return this;
    }

    public CallFormBuilder withCallDirectionId(Integer callDirectionId){
        callForm.setCallDirectionId(callDirectionId);
        return this;
    }


    public CallForm getRes(){
        if(callForm == null){
            build();
        }
        return callForm;
    }
}
