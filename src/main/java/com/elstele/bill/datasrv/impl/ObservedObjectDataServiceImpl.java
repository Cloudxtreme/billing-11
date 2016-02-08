package com.elstele.bill.datasrv.impl;

import com.elstele.bill.dao.interfaces.ObservedObjectDAO;
import com.elstele.bill.datasrv.interfaces.ObservedObjectDataService;
import com.elstele.bill.domain.ObservedObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ObservedObjectDataServiceImpl implements ObservedObjectDataService {

    @Autowired
    ObservedObjectDAO observedObjectDAO;

    final static Logger LOGGER = LogManager.getLogger(ObservedObjectDataServiceImpl.class);

    @Override
    @Transactional
    public void changeObserver(ObservedObject observedObject) {
        LOGGER.info("Object: "+ observedObject + " was changed");
        observedObjectDAO.create(observedObject);
    }
}
