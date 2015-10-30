package com.elstele.bill.controller;

import com.elstele.bill.datasrv.CallBillingService;
import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.datasrv.UploadedFileInfoDataService;
import com.elstele.bill.executors.BillingCallsProcessor;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.FileStatus;
import com.elstele.bill.utils.ResponseToAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.Inet4Address;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class UploadController {

    @Autowired
    ServletContext ctx;

    @Autowired
    UploadedFileInfoDataService uploadedFileInfoDataService;

    @Autowired
    CallDataService callDataService;

    @Autowired
    CallBillingService callBillingService;

    @Autowired
    private BillingCallsProcessor callBillProcessor;

    float progress;

    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public ModelAndView setPageToUpload() {
        ModelAndView model = new ModelAndView("uploadKDF");
        return model;
    }


    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax putFileToFolder(MultipartHttpServletRequest request, HttpServletResponse response, HttpServletRequest requestHttp) throws IOException {
        String fileName = null;
        ctx = requestHttp.getSession().getServletContext();
        String path = ctx.getRealPath("resources\\files");

        Iterator<String> iter = request.getFileNames();
        MultipartFile multipartFile = null;
        while (iter.hasNext()) {
            try {
                multipartFile = request.getFile(iter.next());
                UploadedFileInfoForm uploadedFileInfoForm = new UploadedFileInfoForm();

                fileName = multipartFile.getContentType();
                if (fileName.equalsIgnoreCase("application/octet-stream")) {
                    uploadedFileInfoForm.setPath(multipartFile.getOriginalFilename());
                    uploadedFileInfoForm.setFileName(multipartFile.getName());
                    uploadedFileInfoForm.setFileSize(multipartFile.getSize());
                    uploadedFileInfoForm.setFileStatus(FileStatus.NEW);
                    uploadedFileInfoDataService.addUploadedFileInfo(uploadedFileInfoForm);
                    byte[] bytes = multipartFile.getBytes();
                    String originalName = multipartFile.getOriginalFilename();
                    BufferedOutputStream buffStream =
                            new BufferedOutputStream(new FileOutputStream(path + File.separator + originalName));
                    buffStream.write(bytes);
                    buffStream.close();
                } else {
                    return ResponseToAjax.INCORRECTTYPE;
                }
            } catch (IOException e) {
                return ResponseToAjax.ERROR;
            }
        }
        return ResponseToAjax.SUCCESS;


    }

    @RequestMapping(value = "/uploadedfiles", method = RequestMethod.GET)
    public ModelAndView addLoadedFiles(HttpServletRequest request, HttpServletResponse response) {

        List<UploadedFileInfoForm> uploadedFileInfoForms = new ArrayList<UploadedFileInfoForm>();
        uploadedFileInfoForms = uploadedFileInfoDataService.getUploadedFileInfoList();
        ModelAndView model = new ModelAndView("uploadedfiles");
        model.addObject("uploadedList", uploadedFileInfoForms);
        progress = 0;
        return model;
    }


    @RequestMapping(value = "/uploadedfiles/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDevice(@RequestBody String json, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        Integer id = Integer.parseInt(json);
        UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(id);
        String path = ctx.getRealPath("resources\\files");
        File file = new File(path + File.separator + uploadedFileInfoForm.getPath());
        Path filePath = file.toPath();
        String result = "";
        uploadedFileInfoDataService.setUploadedFileInfoStatus(id);
        try {
            Files.delete(filePath);
            result = "success";
        } catch (IOException e) {
            System.out.println(e);
            return e.toString();
        }
        return result;
    }

    @RequestMapping(value = "/uploadedfiles/handle", method = RequestMethod.POST)
    @ResponseBody
    public void handleFiles(@RequestBody String[] json, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        String path = ctx.getRealPath("resources\\files");
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        long fullFilesSize = 0;
        for ( int i = 0; i< json.length; i++){
            UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(Integer.parseInt(json[i]));
            fullFilesSize += uploadedFileInfoForm.getFileSize();
        }
        for (String str : json) {
            UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(Integer.parseInt(str));
            InputStream fs = null;
            byte[] buffer = new byte[32];
            int count = 0;
            int len;
            long total = 0;
            try {
                fs = new FileInputStream(path + File.separator + uploadedFileInfoForm.getPath());
                String flagString = "";
                String yearFromFileName = "20" + uploadedFileInfoForm.getPath().substring(0, 2);
                String monthFromFileName = uploadedFileInfoForm.getPath().substring(3, 5);
                do {
                    len = fs.read(buffer);

                    //Parse byte packet to string hex
                    char[] hexChars = new char[buffer.length * 2];
                    for (int j = 0; j < buffer.length; j++) {
                        int v = buffer[j] & 0xFF;
                        hexChars[j * 2] = hexArray[v >>> 4];
                        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
                    }

                    String tempStrHEX = new String(hexChars);
                    String numberA = tempStrHEX.substring(5, 12);
                    String numberB = "";
                    String startTime = "";
                    Long duration = null;
                    String dvoCodeA = "";
                    String dvoCodeB = "";


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
                        String vkNum = tempStrHEX.substring(46,49);
                        String ikNum = tempStrHEX.substring(49,52);
                        String inputTrunk = tempStrHEX.substring(42,44);
                        String outputTrunk = tempStrHEX.substring(44,46);

                        String startTimeHour = flagString.substring(12, 14);
                        String startTimeMinutes = flagString.substring(14, 16);
                        String startMonth = flagString.substring(48, 50);
                        String startDate = flagString.substring(46, 48);
                        String prefix = flagString.substring(0,4);

                        if ((startMonth.equalsIgnoreCase("12")) && (monthFromFileName.equalsIgnoreCase("01"))) {
                            int yearInt = Integer.parseInt(yearFromFileName);
                            yearInt = yearInt - 1;
                            yearFromFileName = Integer.toString(yearInt);
                        }

                        startTime = yearFromFileName + "/" + startMonth + "/" + startDate + " " + startTimeHour + ":" + startTimeMinutes;
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                        String testDateStr = "2015/08/15 12:30";
                        Date testDate = simpleDateFormat.parse(testDateStr);
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
                        callForm.setStartTime(testDate);
                        callForm.setIkNum(ikNum);
                        callForm.setVkNum(vkNum);
                        callForm.setInputTrunk(inputTrunk);
                        callForm.setOutputTrunk(outputTrunk);
                        callDataService.addCalls(callForm);
                    }
                    count++;

                    progress = (((count * 32) / (float) (uploadedFileInfoForm.getFileSize() * 1.0)) * 100);


                } while (len != -1);

                fs.close();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            uploadedFileInfoForm.setFileStatus(FileStatus.PROCESSED);
            uploadedFileInfoDataService.setFileStatus(uploadedFileInfoForm);
            progress = 100;
        }

    }

    @RequestMapping(value = "/worker/billCall")
    public @ResponseBody ResponseToAjax costTotalCalculate() {
        callBillProcessor.processCalls();
        return  ResponseToAjax.SUCCESS;
    }

    @RequestMapping(value = "/uploadedfiles/handle/getprogress")
    public @ResponseBody Float getProgress() {
        return progress;
    }



}
