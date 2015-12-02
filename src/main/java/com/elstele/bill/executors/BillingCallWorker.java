package com.elstele.bill.executors;

import com.elstele.bill.datasrv.interfaces.CallBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("billingCallWorker")
@Scope("prototype")

public class BillingCallWorker implements Runnable, Worker {

    @Autowired
    private CallBillingService billService;

    private Integer callId;

    /*public BillingCallWorker(Integer callId){
        System.out.println("Worker constructed with id:" + callId);
        this.callId = callId;
    }*/

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
