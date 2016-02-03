package com.elstele.bill.executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;


public class BillingProcessor {
    @Autowired
    protected WorkerFactory workerFactory;

    protected final Integer poolCapacity = 20;
    final static Logger LOGGER = LogManager.getLogger(BillingProcessor.class);

    protected void shutdownExecutor(ThreadPoolExecutor executor) {
        waitForExecutorHasNoActiveTasks(executor);
        executor.shutdown();
        LOGGER.info("Executor switched off");
    }

    protected void waitForExecutorHasNoActiveTasks(ThreadPoolExecutor executor) {
        while(executor.getActiveCount() > 0){
            try {
                LOGGER.info("-----wait-----");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

}
