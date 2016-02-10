package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.utils.Enums.ObjectOperationType;

import java.util.List;

public interface AuditedObjectDAO extends CommonDAO<AuditedObject> {
    public Integer create(Object object, ObjectOperationType type, String changerName);
    public List<AuditedObject> getAuditedObjectList(int id, String objClassName);
}
