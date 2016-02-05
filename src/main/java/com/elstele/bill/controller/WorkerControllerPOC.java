package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.BillingDataService;
import com.elstele.bill.datasrv.interfaces.CallBillingService;
import com.elstele.bill.executors.BillingCallsProcessor;
import com.elstele.bill.executors.BillingServiceProcessor;
import com.elstele.bill.executors.BillingServiceWorker;
import com.elstele.bill.executors.WorkExecutorPOC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/worker")
public class WorkerControllerPOC {

    @Autowired
    private WorkExecutorPOC executor;

    @Autowired
    private CallBillingService billService;

    @Autowired
    private BillingCallsProcessor callBillProcessor;

    @Autowired
    private BillingServiceProcessor billingServiceProcessor;

    @Autowired
    private BillingDataService billingDataService;

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
                           HttpSession session,
                           @RequestParam(value = "callId") Integer id){

        //this call need to be in runnable
        //billService.updateCallWithItCost(id);
        callBillProcessor.processCalls(session);



        return "";
    }

    @RequestMapping(value="/billService", method = RequestMethod.GET)
    @ResponseBody
    public String billServices(HttpServletRequest request){
        //this call need to be in runnable
        //billService.updateCallWithItCost(id);
        Integer processed = billingServiceProcessor.billAllServices();
        return "";
    }

    @RequestMapping(value="/copyBalance", method = RequestMethod.GET)
    @ResponseBody
    public String copyBalance(HttpServletRequest request){
        billingDataService.copyCurAccountBalToHistBalance();
        return "done";
    }



}
