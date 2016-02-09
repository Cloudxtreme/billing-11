package com.elstele.bill.datasrv.impl;

import com.elstele.bill.Builders.AuditedObjectBuilder;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.datasrv.interfaces.AuditedObjectDataService;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
}
