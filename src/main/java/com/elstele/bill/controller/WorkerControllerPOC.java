package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.CallBillingService;
import com.elstele.bill.executors.BillingCallsProcessor;
import com.elstele.bill.executors.WorkExecutorPOC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/worker")
public class WorkerControllerPOC {

    @Autowired
    private WorkExecutorPOC executor;

    @Autowired
    private CallBillingService billService;

    @Autowired
    private BillingCallsProcessor callBillProcessor;


    @RequestMapping(value="/starttask", method = RequestMethod.GET)
    @ResponseBody
    public String startTask(HttpServletRequest request,
                                            @RequestParam(value = "taskParam") int taskParam){
        executor.startTask(taskParam);
        String result = "Task N started with param " + taskParam;
        return result;
    }



    @RequestMapping(value="/taskProgress", method = RequestMethod.GET)
    @ResponseBody
    public String getTaskProgress(HttpServletRequest request,
                            @RequestParam(value = "taskId") int taskId){

        int taskStatus = executor.getTaskProgresById(taskId);
        String result = "Task with " + taskId + " has status " + taskStatus;
        return result;
    }


    @RequestMapping(value="/billCall", method = RequestMethod.GET)
    @ResponseBody
    public String billCall(HttpServletRequest request,
                                  @RequestParam(value = "callId") Integer id){

        //this call need to be in runnable
        //billService.updateCallWithItCost(id);
        callBillProcessor.processCalls();



        return "";
    }






}
