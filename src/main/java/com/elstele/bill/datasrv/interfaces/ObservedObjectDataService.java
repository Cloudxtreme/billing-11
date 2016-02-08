package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.ObservedObject;
import org.springframework.stereotype.Service;

@Service
public interface ObservedObjectDataService {
    public void changeObserver(ObservedObject observedObject);
}
