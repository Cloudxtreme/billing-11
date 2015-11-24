package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.FileStatus;
import com.elstele.bill.utils.ResponseToAjax;
import com.elstele.bill.utils.USDRate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Iterator;
import java.util.List;

@Controller
public class FileUploadController {
    @Autowired
    UploadedFileInfoDataService uploadedFileInfoDataService;
    @Autowired
    CallDataService callDataService;
    @Autowired
    ServletContext ctx;

    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.GET)
    public ModelAndView fileCSVFirstView() {
        ModelAndView model = new ModelAndView("uploadCSVFile");
        List<String> listDate = callDataService.getYearsList();
        model.addObject("yearLsit", listDate);
        return model;
    }


    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public ModelAndView setPageToUpload() {
        ModelAndView model = new ModelAndView("uploadKDF");
        return model;
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax putFileToFolder(MultipartHttpServletRequest request,HttpServletRequest requestHttp) throws IOException {
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

}
