package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.springframework.stereotype.Service;

@Service
public interface AuditedObjectDataService {
    public void changeObserver(Object object, ObjectOperationType type, String changerName);
}
