package com.elstele.bill.executors;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static com.elstele.bill.utils.Constants.BILLING_CALL_WORKER;

@Service
@Scope("singleton")
public class BillingCallsProcessor {

    @Autowired
    private CallDataService callDataService;
    @Autowired
    private WorkerFactory workerFactory;

    private final Integer poolCapacity = 20;
    private final Integer pageSize = 50;
    private Integer processedCallsCounter;



    public void processCalls(){

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolCapacity);

        processedCallsCounter = 0;
        Integer callsCount =  callDataService.getUnbilledCallsCount();
        Integer pagesCount = callsCount/pageSize;
        System.out.println("-------------Initial call countForTO:" + callsCount);


        for (int i=0; i<pagesCount+1; i++){
            System.out.println("-------------Active Count in Executor:" + executor.getActiveCount());
            waitForExecutorHasNoActiveTasks(executor);

            Integer tempCallsCount =  callDataService.getUnbilledCallsCount();
            List<Integer> curCallIds = callDataService.getUnbilledCallsIdList(pageSize, 0);
            System.out.println("-------------Call countForTO at :" + i + " -- " + tempCallsCount + "(" + curCallIds.size() + ")");

            putCallsToExecutor(executor, curCallIds);

        }

        System.out.println("Processed calls:" + processedCallsCounter);
        shutdownExecutor(executor);
    }

    private void shutdownExecutor(ThreadPoolExecutor executor) {
        waitForExecutorHasNoActiveTasks(executor);
        executor.shutdown();
        System.out.println("Executor switched off");
    }

    private void putCallsToExecutor(ThreadPoolExecutor executor, List<Integer> curCallIds) {
        for (Integer callId : curCallIds){
            BillingCallWorker worker = (BillingCallWorker)workerFactory.getWorker(BILLING_CALL_WORKER);
            worker.setCallId(callId);
            executor.execute(worker);
            processedCallsCounter = processedCallsCounter+1;
        }
    }

    private void waitForExecutorHasNoActiveTasks(ThreadPoolExecutor executor) {
        while(executor.getActiveCount() > 0){
            try {
                System.out.println("-----wait-----");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
