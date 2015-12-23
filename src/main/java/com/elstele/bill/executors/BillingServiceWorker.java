package com.elstele.bill.executors;

import com.elstele.bill.datasrv.interfaces.BillingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.elstele.bill.utils.Constants.BILLING_SERVICE_WORKER;

/**
 * Created by ivan on 15/12/16.
 */

@Service(BILLING_SERVICE_WORKER)
@Scope("prototype")
public class BillingServiceWorker implements  Runnable, Worker {

    @Autowired
    private BillingDataService billingDataService;

    private Integer serviceId;

    @Override
    public void setTargetId(Integer id) {
        serviceId = id;
    }

    @Override
    public void run() {
        billingDataService.createTransactionAndDecreaseBalance(serviceId);
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getServiceId() {
        return serviceId;
    }
}
