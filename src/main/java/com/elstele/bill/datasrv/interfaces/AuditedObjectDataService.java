package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.AuditedObjectForm;

import java.util.Date;
import java.util.List;

public interface AuditedObjectDataService {
    List<AuditedObjectForm> getAuditedObject(int id, String objClassName);
    String getCreatedBy(List<AuditedObjectForm> auditedObjectFormList);
    Date getCreatedDate(List<AuditedObjectForm> auditedObjectFormList);
}
