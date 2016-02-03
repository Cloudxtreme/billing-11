package com.elstele.bill.executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkerPOC implements Runnable{

    private int taskId;
    private int progress;
    final static Logger LOGGER = LogManager.getLogger(WorkerPOC.class);

    public WorkerPOC(int taskId) {
        this.taskId = taskId;
    }

    public void run() {
        try {
            while(progress != 100) {
                Thread.sleep(1000);
                progressIncrease();
                LOGGER.info("Task id:" + taskId + " progress:" + progress);
            }
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private synchronized void progressIncrease(){
        progress = progress+1;
    }

    public int getProgress() {
        return progress;
    }

    public int getTaskId() {
        return taskId;
    }
}
