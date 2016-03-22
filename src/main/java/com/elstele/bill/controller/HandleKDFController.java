package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.CallBillingService;
import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.executors.BillingCallsProcessor;
import com.elstele.bill.filesWorkers.KDFFileParser;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;



@Controller
public class HandleKDFController {

    @Autowired
    UploadedFileInfoDataService uploadedFileInfoDataService;

    @Autowired
    CallDataService callDataService;

    @Autowired
    CallBillingService callBillingService;

    @Autowired
    KDFFileParser kdfFileParser;

    @Autowired
    private BillingCallsProcessor callBillProcessor;

    final static Logger LOGGER = LogManager.getLogger(HandleKDFController.class);

    @RequestMapping(value = "/uploadedfiles", method = RequestMethod.GET)
    public ModelAndView addLoadedFiles(HttpSession session) {
        List<UploadedFileInfoForm> uploadedFileInfoForms;
        uploadedFileInfoForms = uploadedFileInfoDataService.getUploadedFileInfoList(Constants.KDF_FILE_TYPE);
        ModelAndView model = new ModelAndView("uploadedKDFFiles");
        model.addObject("uploadedList", uploadedFileInfoForms);
        UserStateStorage.setProgressToObjectInMap(session, 0);
        return model;
    }

    @RequestMapping(value = "/uploadedfiles/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDevice(@RequestBody String[] json) {
        try {
            for(String ids : json) {
                Integer id = Integer.parseInt(ids);
                uploadedFileInfoDataService.setUploadedFileInfoStatusDelete(id);
            }
            return "success";
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return "error";
        }
    }

    @RequestMapping(value = "/uploadedfiles/handle", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax handleFiles(@RequestBody String[] selectedFileID, HttpSession session) {
        return kdfFileParser.parse(selectedFileID, session);
    }

    @RequestMapping(value = "/worker/billCall")
    @ResponseBody
    public ResponseToAjax costTotalCalculate(HttpSession session) {
        return callBillProcessor.processCalls(session);
    }

    @RequestMapping(value = "/uploadedfiles/handle/getprogress")
    public @ResponseBody Float getKDFProgress(HttpSession session) {
        return UserStateStorage.getProgressBySession(session);
    }

    @RequestMapping(value = "/uploadedfiles/billCall/getprogress")
    public @ResponseBody Float getBillCallProgress(HttpSession session) {
        return UserStateStorage.getProgressBySession(session);
    }
}
