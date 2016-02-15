package com.elstele.bill.dao.impl;

import com.elstele.bill.builder.bean.AuditedObjectBuilder;
import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AuditedObjectDAOImpl extends CommonDAOImpl<AuditedObject> implements AuditedObjectDAO {
    private AuditedObjectBuilder builder = new AuditedObjectBuilder();
    final static Logger LOGGER = LogManager.getLogger(AuditedObjectDAOImpl.class);

    @Override
    public Integer create(CommonDomainBean object, ObjectOperationType type, String changerName) {
        AuditedObject auditedObject = builder.build()
                .withChangedObject(object)
                .withChangedObjID(object.getId())
                .withChangesDate(new Date())
                .withOperationType(type)
                .withChangerName(changerName)
                .getRes();
        LOGGER.info("Object: " + object + " was changed and added to DB");
        return (Integer) getSessionFactory().getCurrentSession().save(auditedObject);
    }

    @Override
    public List<AuditedObject> getAuditedObjectList(int id, String objClassName) {
        Query q = getSessionFactory().getCurrentSession().createQuery("from AuditedObject a where a.changedObjID = :objId and a.objClass = :objClass " +
                "order by a.changesDate")
                .setParameter("objId", id)
                .setParameter("objClass", objClassName);
        return (List<AuditedObject>)q.list();
    }

}
