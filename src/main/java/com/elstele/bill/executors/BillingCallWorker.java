package com.elstele.bill.executors;

import com.elstele.bill.datasrv.interfaces.CallBillingService;
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

    @Override
    public void setTargetId(Integer id) {
        callId = id;
    }

    public void run() {
        System.out.println("Worker runned with id:" + callId);
        billService.updateCallWithItCost(callId);
    }

    public Integer getCallId() {
        return callId;
    }

    public void setCallId(Integer callId) {
        this.callId = callId;
    }
}
