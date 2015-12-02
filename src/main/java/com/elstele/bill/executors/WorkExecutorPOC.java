package com.elstele.bill.executors;


import com.elstele.bill.executors.WorkerPOC;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Scope("singleton")
public class WorkExecutorPOC {

    ConcurrentHashMap<Integer, WorkerPOC> taskMap = new ConcurrentHashMap<Integer, WorkerPOC>();
    ThreadPoolExecutor executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(5);


    public void startTask(Integer taskId){
        WorkerPOC worker = new WorkerPOC(taskId);
        taskMap.put(taskId, worker);
        executor.execute(worker);
    }

    public Integer getTaskProgresById(int taskId){
        WorkerPOC targetWorker = taskMap.get(taskId);
        if (targetWorker != null){
            return targetWorker.getProgress();
        }
        return 0;
    }



}
