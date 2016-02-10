package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.AuditedObjectForm;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AuditedObjectDataService {
    public void changeObserver(Object object, ObjectOperationType type, String changerName);
    public List<AuditedObjectForm> getAuditedObject(int id, String objClassName);
    public String getCreatedBy(List<AuditedObjectForm> auditedObjectFormList);
    public Date getCreatedDate(List<AuditedObjectForm> auditedObjectFormList);
}
