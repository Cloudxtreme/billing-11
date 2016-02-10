package com.elstele.bill.datasrv.impl;

import com.elstele.bill.Builders.AuditedObjectBuilder;
import com.elstele.bill.assembler.AuditedObjectAssembler;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.datasrv.interfaces.AuditedObjectDataService;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AuditedObjectDataServiceImpl implements AuditedObjectDataService {

    @Autowired
    AuditedObjectDAO auditedObjectDAO;
    private AuditedObjectBuilder builder = new AuditedObjectBuilder();

    final static Logger LOGGER = LogManager.getLogger(AuditedObjectDataServiceImpl.class);

    @Override
    @Transactional
    public void changeObserver(Object object, ObjectOperationType type, String changerName) {
        AuditedObject auditedObject = builder.build()
                .withChangedObject(object)
                .withChangesDate(new Date())
                .withOperationType(type)
                .withChangerName(changerName)
                .getRes();
        LOGGER.info("Object: "+ auditedObject + " was changed");
        auditedObjectDAO.create(auditedObject);
    }

    @Override
    @Transactional
    public List<AuditedObjectForm> getAuditedObject(int id, String objClassName) {
        List<AuditedObjectForm> formList = new ArrayList<>();
        List<AuditedObject> auditedObjectList = auditedObjectDAO.getAuditedObjectList(id , objClassName);
        AuditedObjectAssembler assembler = new AuditedObjectAssembler();
        for(AuditedObject auditedObject : auditedObjectList){
            formList.add(assembler.fromBeanToForm(auditedObject));
        }
        LOGGER.info("AuditedObject size is " + formList.size());
        return formList;
    }

    @Override
    public String getCreatedBy(List<AuditedObjectForm> auditedObjectFormList){
        for(AuditedObjectForm form : auditedObjectFormList){
            if(form.getChangesType().equals(ObjectOperationType.CREATE)){
                return form.getChangedBy();
            }
        }
        return "";
    }

    @Override
    public Date getCreatedDate(List<AuditedObjectForm> auditedObjectFormList){
        for(AuditedObjectForm form : auditedObjectFormList){
            if(form.getChangesType().equals(ObjectOperationType.CREATE)){
                return form.getChangesDate();
            }
        }
        return null;
    }
}
