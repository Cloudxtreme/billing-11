package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.ReportDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.filesWorkers.FileUploader;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.Enums.FileStatus;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import com.elstele.bill.utils.PropertiesHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import sun.rmi.runtime.Log;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Iterator;
import java.util.List;

@Controller
public class FileUploadController {
    @Autowired
    UploadedFileInfoDataService uploadedFileInfoDataService;
    @Autowired
    CallDataService callDataService;
    @Autowired
    FileUploader fileUploader;
    @Autowired
    ReportDataService reportDataService;



    @RequestMapping(value = "/uploadcsvfile", method = RequestMethod.GET)
    public ModelAndView fileCSVFirstView() {
        ModelAndView model = new ModelAndView("uploadCSVFile");
        List<String> listDate = callDataService.getYearsList();
        model.addObject("yearList", listDate);
        reportDataService.setProgress(0);
        return model;
    }


    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public ModelAndView setPageToUpload() {
        ModelAndView model = new ModelAndView("uploadKDF");
        return model;
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax putFileToFolder(MultipartHttpServletRequest request){
        return fileUploader.uploadFileToServer(request);
    }

}
