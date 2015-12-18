package com.elstele.bill.executors;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by ivan on 15/12/18.
 */
public class BillingProcessor {
    @Autowired
    protected WorkerFactory workerFactory;

    protected final Integer poolCapacity = 20;

    protected void shutdownExecutor(ThreadPoolExecutor executor) {
        waitForExecutorHasNoActiveTasks(executor);
        executor.shutdown();
        System.out.println("Executor switched off");
    }

    protected void waitForExecutorHasNoActiveTasks(ThreadPoolExecutor executor) {
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
