package com.elstele.bill.executors;

/**
 * Created by X240 on 24/09/2015.
 */
public class WorkerPOC implements Runnable{

    private int taskId;
    private int progress;

    public WorkerPOC(int taskId) {
        this.taskId = taskId;
    }

    public void run() {
        try {
            while(progress != 100) {
                Thread.sleep(1000);
                progressIncrease();
                System.out.println("Task id:" + taskId + " progress:" + progress);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
