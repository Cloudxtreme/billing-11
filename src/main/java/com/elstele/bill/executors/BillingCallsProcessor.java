package com.elstele.bill.executors;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import static com.elstele.bill.utils.Constants.BILLING_CALL_WORKER;

@Service
@Scope("singleton")
public class BillingCallsProcessor extends BillingProcessor {

    @Autowired
    private CallDataService callDataService;
    private final Integer pageSize = 100;
    private Integer processedCallsCounter = 0;
    private float progress;
    private int callsCount;
    final static Logger LOGGER = LogManager.getLogger(BillingCallsProcessor.class);

    public void processCalls(){
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolCapacity);

        //TODO convert sysout to LOGGER and add more verbal output
        List<Integer> callsToBeBilledIds = callDataService.getUnbilledCallsIdList(0,0);
        callsCount =  callsToBeBilledIds.size();
        Integer pagesCount = (callsCount/pageSize)+1;
        LOGGER.info("-------------Initial call countForTO:" + callsCount + " Pages count:" + pagesCount);


        for (int i=0; i<pagesCount; i++){
            LOGGER.info("-------------Active Count in Executor:" + executor.getActiveCount());
            waitForExecutorHasNoActiveTasks(executor);

            Integer tempCallsCount =  callDataService.getUnbilledCallsCount();
            //List<Integer> curCallIds = callDataService.getUnbilledCallsIdList(pageSize, 0);
            int from = i*pageSize;
            int to = (i+1)*pageSize;

            if (to > callsToBeBilledIds.size()-1){
                to = callsToBeBilledIds.size();
            }
            List<Integer> curCallIds = callsToBeBilledIds.subList(from, to);
            LOGGER.info("-------------Call taked from :" + from + " to " + to);
            LOGGER.info("-------------Call countForTO at :" + i + " -- " + tempCallsCount + "(" + curCallIds.size() + ")");
            LOGGER.info("-------------Calls processed " + processedCallsCounter + " from " + callsCount + " progress:" + getCallsBillingProgress() + "%");

            putCallsToExecutor(executor, curCallIds);
        }
        LOGGER.info("Processed calls:" + processedCallsCounter + " progress:" + getCallsBillingProgress() + "%");
        shutdownExecutor(executor);
    }

    public float getCallsBillingProgress(){
        return (float) processedCallsCounter / callsCount * 100;
    }

    private void putCallsToExecutor(ThreadPoolExecutor executor, List<Integer> curCallIds) {
        for (Integer callId : curCallIds){
            BillingCallWorker worker = (BillingCallWorker)workerFactory.getWorker(BILLING_CALL_WORKER);
            worker.setCallId(callId);
            executor.execute(worker);
            processedCallsCounter = processedCallsCounter+1;
        }
    }

    public void setProcessedCallsCounter(Integer processedCallsCounter) {
        this.processedCallsCounter = processedCallsCounter;
    }
}
