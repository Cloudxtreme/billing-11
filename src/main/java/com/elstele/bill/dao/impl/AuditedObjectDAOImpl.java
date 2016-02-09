package com.elstele.bill.dao.impl;

import com.elstele.bill.Builders.AuditedObjectBuilder;
import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuditedObjectDAOImpl extends CommonDAOImpl<AuditedObject> implements AuditedObjectDAO {
    private AuditedObjectBuilder builder = new AuditedObjectBuilder();
    final static Logger LOGGER = LogManager.getLogger(AuditedObjectDAOImpl.class);

    @Override
    public Integer create(Object object, ObjectOperationType type, String changerName) {
        AuditedObject auditedObject = builder.build()
                .withChangedObject(object)
                .withChangesDate(new Date())
                .withOperationType(type)
                .withChangerName(changerName)
                .getRes();
        LOGGER.info("Object: " + object + " was changed and added to DB");
        return (Integer) getSessionFactory().getCurrentSession().save(auditedObject);
    }
}
