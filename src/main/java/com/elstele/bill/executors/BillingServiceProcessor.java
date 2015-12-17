package com.elstele.bill.executors;

import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.datasrv.interfaces.ServiceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static com.elstele.bill.utils.Constants.BILLING_SERVICE_WORKER;
/**
 * Created by ivan on 15/12/16.
 */
@Service
@Scope("singleton")
public class BillingServiceProcessor {

    @Autowired
    private WorkerFactory workerFactory;
    @Autowired
    private ServiceDataService sds;

    private final Integer poolCapacity = 20;
    private Integer serviceCount = 0;

    public Integer billAllServices(){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolCapacity);

        //get List off all active services
        List<Integer> activeServicesIdList = sds.listActiveServicesIds();

        for (Integer curServiceId : activeServicesIdList){
            putServiceIdToExcecutor(executor, curServiceId);
        }
        return serviceCount;
    }

    private void putServiceIdToExcecutor(ThreadPoolExecutor executor, Integer curServiceId) {
        BillingServiceWorker worker = (BillingServiceWorker)workerFactory.getWorker(BILLING_SERVICE_WORKER);
        worker.setServiceId(curServiceId);
        executor.execute(worker);
        serviceCount++;
    }

}
