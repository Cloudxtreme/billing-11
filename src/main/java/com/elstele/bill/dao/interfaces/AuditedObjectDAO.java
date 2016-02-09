package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.AuditedObject;
import com.elstele.bill.utils.Enums.ObjectOperationType;

public interface AuditedObjectDAO extends CommonDAO<AuditedObject> {
    public Integer create(Object object, ObjectOperationType type, String changerName);
}
