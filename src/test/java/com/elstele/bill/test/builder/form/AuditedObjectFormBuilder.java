package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.utils.Enums.ObjectOperationType;

import java.util.Date;

public class AuditedObjectFormBuilder {
    private AuditedObjectForm form;

    public AuditedObjectFormBuilder build() {
        form = new AuditedObjectForm();
        return this;
    }

    public AuditedObjectFormBuilder withId(int val) {
        form.setId(val);
        return this;
    }

    public AuditedObjectFormBuilder withChangedObject(String val) {
        form.setChangedObject(val);
        return this;
    }
    public AuditedObjectFormBuilder withObjClass(String val){
        form.setObjClass(val.getClass().getSimpleName());
        return this;
    }

    public AuditedObjectFormBuilder withChangedObjID(int val){
        form.setChangedObjID(val);
        return this;
    }

    public AuditedObjectFormBuilder withChangesDate(Date val) {
        form.setChangesDate(val);
        return this;
    }

    public AuditedObjectFormBuilder withOperationType(ObjectOperationType val) {
        form.setChangesType(val);
        return this;
    }

    public AuditedObjectFormBuilder withChangerName(String changerName) {
        form.setChangedBy(changerName);
        return this;
    }

    public AuditedObjectForm getRes() {
        if (form == null) {
            build();
        }
        return form;
    }
}
