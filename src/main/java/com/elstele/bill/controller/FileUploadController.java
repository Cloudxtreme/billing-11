package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.ReportDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.filesWorkers.FileUploader;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
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
    public ModelAndView fileCSVFirstView(HttpSession session) {
        ModelAndView model = new ModelAndView("uploadCSVFile");
        List<String> listDate = callDataService.getYearsList();
        model.addObject("yearList", listDate);

        UserStateStorage.setProgressToObjectInMap(session, 0);

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
