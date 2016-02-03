package com.elstele.bill.executors;

import com.elstele.bill.datasrv.interfaces.CallBillingService;
import com.elstele.bill.exceptions.DirectionCallException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import static com.elstele.bill.utils.Constants.BILLING_CALL_WORKER;

@Service(BILLING_CALL_WORKER)
@Scope("prototype")
public class BillingCallWorker implements Runnable, Worker {

    @Autowired
    private CallBillingService billService;

    private Integer callId;
    final static Logger LOGGER = LogManager.getLogger(BillingCallWorker.class);


    @Override
    public void setTargetId(Integer id) {
        callId = id;
    }

    public void run(){
        //LOGGER.info("Worker runned with id:" + callId);
        try {
            billService.updateCallWithItCost(callId);
        } catch (DirectionCallException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }
}
