package com.elstele.bill.controller;

import com.elstele.bill.datasrv.WorkExecutorPOC;
import com.elstele.bill.form.AccountForm;
import com.elstele.bill.form.LocalUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/worker")
public class WorkerControllerPOC {

    @Autowired
    private WorkExecutorPOC executor;

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





}
