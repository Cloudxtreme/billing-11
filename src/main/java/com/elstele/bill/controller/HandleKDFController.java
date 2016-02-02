package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.CallBillingService;
import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.executors.BillingCallsProcessor;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.Enums.FileStatus;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.text.SimpleDateFormat;
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
    private BillingCallsProcessor callBillProcessor;

    @Autowired
    private LocalDirPathProvider pathProvider;

    final static Logger LOGGER = LogManager.getLogger(HandleKDFController.class);
    final static Integer BUFFER_SIZE = 32;
    float progress;

    @RequestMapping(value = "/uploadedfiles", method = RequestMethod.GET)
    public ModelAndView addLoadedFiles() {
        List<UploadedFileInfoForm> uploadedFileInfoForms;
        uploadedFileInfoForms = uploadedFileInfoDataService.getUploadedFileInfoList();
        ModelAndView model = new ModelAndView("uploadedKDFFiles");
        model.addObject("uploadedList", uploadedFileInfoForms);
        progress = 0;
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
            LOGGER.error(e.toString() + " Method deleteDevice");
            return "error";
        }
    }

    @RequestMapping(value = "/uploadedfiles/handle", method = RequestMethod.POST)
    @ResponseBody
    public void handleFiles(@RequestBody String[] json) {
        //TODO next method too complicated and long
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        long fullFilesSize = 0;
        for (int i = 0; i < json.length; i++) {
            UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(Integer.parseInt(json[i]));
            fullFilesSize += uploadedFileInfoForm.getFileSize();
        }
        int count = 0;
        for (String fileId : json) {
            UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(Integer.parseInt(fileId));
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            try {
                InputStream fs = new FileInputStream(pathProvider.getKDFDirectoryPath() + File.separator + uploadedFileInfoForm.getPath());
                String flagString = "";
                String yearFromFileName = "20" + uploadedFileInfoForm.getPath().substring(0, 2);
                String monthFromFileName = uploadedFileInfoForm.getPath().substring(3, 5);
                do {
                    len = fs.read(buffer);
                    char[] hexChars = new char[buffer.length * 2];
                    for (int j = 0; j < buffer.length; j++) {
                        int v = buffer[j] & 0xFF;
                        hexChars[j * 2] = hexArray[v >>> 4];
                        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
                    }
                    String tempStrHEX = new String(hexChars);
                    String numberA = tempStrHEX.substring(5, 12);
                    String numberB;
                    String startTime;
                    Long duration;
                    String dvoCodeA;
                    String dvoCodeB;

                    if (tempStrHEX.startsWith("A54C") && !numberA.equalsIgnoreCase("0000000")) {
                        //first packet
                        flagString = tempStrHEX;
                    }

                    if (tempStrHEX.startsWith("A54C") && numberA.equalsIgnoreCase("0000000")) {
                        //second packet

                        String aon = Character.toString(flagString.charAt(4));
                        numberA = flagString.substring(5, 12);
                        numberB = tempStrHEX.substring(20, 38).replaceAll("[^0-9]", "");
                        dvoCodeA = flagString.substring(42, 44);
                        dvoCodeB = flagString.substring(44, 46);
                        duration = Long.parseLong((tempStrHEX.substring(52, 54) + tempStrHEX.substring(16, 20)), 16);
                        String vkNum = tempStrHEX.substring(46, 49);
                        String ikNum = tempStrHEX.substring(49, 52);
                        String inputTrunk = tempStrHEX.substring(42, 44);
                        String outputTrunk = tempStrHEX.substring(44, 46);

                        String startTimeHour = flagString.substring(12, 14);
                        String startTimeMinutes = flagString.substring(14, 16);
                        String startMonth = flagString.substring(48, 50);
                        String startDate = flagString.substring(46, 48);
                        String prefix = flagString.substring(0, 4);

                        if ((startMonth.equalsIgnoreCase("12")) && (monthFromFileName.equalsIgnoreCase("01"))) {
                            int yearInt = Integer.parseInt(yearFromFileName);
                            yearInt = yearInt - 1;
                            yearFromFileName = Integer.toString(yearInt);
                        }
                        startTime = yearFromFileName + "/" + startMonth + "/" + startDate + " " + startTimeHour + ":" + startTimeMinutes;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        Date startTimeInDateFormat = simpleDateFormat.parse(startTime);
                        String calling = "";
                        String called = "";
                        if (aon.equalsIgnoreCase("f")) {
                            calling = numberB;
                            called = numberA;
                        } else {
                            calling = numberA;
                            called = numberB;
                        }

                        CallForm callForm = new CallForm();
                        callForm.setAonKat(aon);
                        callForm.setNumberA(calling);
                        callForm.setNumberB(called);
                        callForm.setDuration(duration);
                        callForm.setDvoCodeA(dvoCodeA);
                        callForm.setDvoCodeB(dvoCodeB);
                        callForm.setStartTime(startTimeInDateFormat);
                        callForm.setIkNum(ikNum);
                        callForm.setVkNum(vkNum);
                        callForm.setInputTrunk(inputTrunk);
                        callForm.setOutputTrunk(outputTrunk);
                        callDataService.addCalls(callForm);
                    }
                    count++;
                    progress = (((count * BUFFER_SIZE) / (float) (fullFilesSize * 1.0)) * 100);
                } while (len != -1);
                fs.close();
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }
            uploadedFileInfoForm.setFileStatus(FileStatus.PROCESSED);
            uploadedFileInfoDataService.updateFile(uploadedFileInfoForm);
            progress = 100;
        }
    }

    @RequestMapping(value = "/worker/billCall")
    public
    @ResponseBody
    ResponseToAjax costTotalCalculate() {
        callBillProcessor.processCalls();
        return ResponseToAjax.SUCCESS;
    }

    @RequestMapping(value = "/uploadedfiles/handle/getprogress")
    public
    @ResponseBody
    Float getProgress() {
        return progress;
    }
}
