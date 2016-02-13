package com.elstele.bill.builder.bean;

import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.elstele.bill.utils.Enums.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.Date;


public class AuditedObjectBuilder {
    private AuditedObject auditedObject;
    private ObjectMapper mapper;
    final static Logger LOGGER = LogManager.getLogger(AuditedObjectBuilder.class);

    public AuditedObjectBuilder build() {
        mapper = new ObjectMapper();
        auditedObject = new AuditedObject();
        auditedObject.setChangesDate(new Date());
        return this;
    }

    public AuditedObjectBuilder withId(int val) {
        auditedObject.setId(val);
        return this;
    }

    public AuditedObjectBuilder withStatus(Status val) {
        auditedObject.setStatus(val);
        return this;
    }

    public AuditedObjectBuilder withChangedObject(Object val) {
        try {
            String objectInString = mapper.writeValueAsString(val);
            auditedObject.setChangedObject(objectInString);
            auditedObject.setObjClass(val.getClass().getSimpleName());
            auditedObject.setClassRerence(val.getClass().getName());
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return this;
    }

    public AuditedObjectBuilder withChangedObjID(int val){
        auditedObject.setChangedObjID(val);
        return this;
    }

    public AuditedObjectBuilder withChangesDate(Date val) {
        auditedObject.setChangesDate(val);
        return this;
    }

    public AuditedObjectBuilder withOperationType(ObjectOperationType val) {
        auditedObject.setChangesType(val);
        return this;
    }

    public AuditedObjectBuilder withChangerName(String changerName) {
        auditedObject.setChangedBy(changerName);
        return this;
    }

    public AuditedObject getRes() {
        if (auditedObject == null) {
            build();
        }
        return auditedObject;
    }
}
