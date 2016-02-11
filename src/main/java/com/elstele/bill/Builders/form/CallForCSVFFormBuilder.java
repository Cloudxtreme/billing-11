package com.elstele.bill.Builders.form;

import com.elstele.bill.form.CallForCSVForm;

import java.util.Date;

public class CallForCSVFFormBuilder {
    private CallForCSVForm callForm;

    public CallForCSVFFormBuilder build() {
        callForm = new CallForCSVForm();
        return this;
    }


    public CallForCSVFFormBuilder withNumberA(String numberA) {
        callForm.setNumberA(numberA);
        return this;
    }

    public CallForCSVFFormBuilder withNumberB(String numberB) {
        callForm.setNumberB(numberB);
        return this;
    }

    public CallForCSVFFormBuilder withStartTime(Date date) {
        callForm.setStartTime(date);
        return this;
    }

    public CallForCSVFFormBuilder withDuration(int duration) {
        callForm.setDuration(duration);
        return this;
    }

    public CallForCSVFFormBuilder withDirPrefix(String val) {
        callForm.setDirPrefix(val);
        return this;
    }

    public CallForCSVFFormBuilder withDirDescrpOrg(String val) {
        callForm.setDirDescrpOrg(val);
        return this;
    }

    public CallForCSVFFormBuilder withCallCostTotal(String val) {
        callForm.setCostCallTotal(val);
        return this;
    }

    public CallForCSVFFormBuilder withProvider(String val) {
        callForm.setProvider(val);
        return this;
    }

    public CallForCSVForm getRes() {
        if (callForm == null) {
            build();
        }
        return callForm;
    }
}
