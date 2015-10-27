package com.elstele.bill.executors;


public interface WorkerFactory {
    public Worker getWorker (String workerName);
}
