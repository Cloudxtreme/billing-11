package com.elstele.bill.controller;

import com.elstele.bill.dao.UploadedFileInfoDAO;
import com.elstele.bill.datasrv.UploadedFileInfoDataService;
import com.elstele.bill.domain.UploadedFileInfo;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.FileStatus;
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
import java.util.*;


@Controller
public class UploadController {

    @Autowired
    ServletContext ctx;

    @Autowired
    UploadedFileInfoDataService uploadedFileInfoDataService;

    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public ModelAndView setPageToUpload() {
        ModelAndView model = new ModelAndView("uploadKDF");

        return model;
    }


    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public String putFileToFolder(MultipartHttpServletRequest request, HttpServletResponse response, HttpServletRequest requestHttp) throws IOException {
        String fileName = null;
        ctx = requestHttp.getSession().getServletContext();
        String path = ctx.getRealPath("resources\\files");

        Iterator<String> iter = request.getFileNames();
        MultipartFile multipartFile = null;
        while(iter.hasNext()) {
            multipartFile = request.getFile(iter.next());
            UploadedFileInfoForm uploadedFileInfoForm = new UploadedFileInfoForm();
            uploadedFileInfoForm.setPath(multipartFile.getOriginalFilename());
            uploadedFileInfoForm.setFileName(multipartFile.getName());
            uploadedFileInfoForm.setFileSize(multipartFile.getSize());
            uploadedFileInfoForm.setFileStatus(FileStatus.NEW);
            uploadedFileInfoDataService.addUploadedFileInfo(uploadedFileInfoForm);

            try {
                assert multipartFile != null;
                fileName = multipartFile.getContentType();
                if (fileName.equalsIgnoreCase("application/octet-stream")) {
                    byte[] bytes = multipartFile.getBytes();
                    String originalName = multipartFile.getOriginalFilename();
                    BufferedOutputStream buffStream =
                            new BufferedOutputStream(new FileOutputStream(path + File.separator + originalName));
                    buffStream.write(bytes);
                    buffStream.close();
                } else {
                    return "2";
                }
            } catch (IOException e) {
                return "3";
            }
        }

        return "1";

    }

    @RequestMapping(value = "/uploadedfiles", method = RequestMethod.GET)
    public ModelAndView addLoadedFiles(HttpServletRequest request, HttpServletResponse response){

        List<UploadedFileInfoForm> uploadedFileInfoForms = new ArrayList<UploadedFileInfoForm>();
        uploadedFileInfoForms = uploadedFileInfoDataService.getUploadedFileInfoList();
        ModelAndView model = new ModelAndView("uploadedfiles");
        model.addObject("uploadedList", uploadedFileInfoForms);
        return model;
    }



    @RequestMapping(value="/uploadedfiles/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDevice(@RequestBody String json, HttpSession session, HttpServletResponse response, HttpServletRequest request){
        Integer id = Integer.parseInt(json);
        UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(id);
        String path = ctx.getRealPath("resources\\files");
        File file = new File(path + File.separator + uploadedFileInfoForm.getPath());
        String result = "";
        uploadedFileInfoDataService.setUploadedFileInfoStatus(id);
        if (file.delete()) {
            result = "success";
        } else {
            result = "fail";
        }
        return result;
    }

    @RequestMapping(value = "/uploadedfiles/handle", method = RequestMethod.POST)
    @ResponseBody
    public void handleFiles(@RequestBody String[] json, HttpSession session, HttpServletResponse response, HttpServletRequest request){
        List<UploadedFileInfoForm> list = new ArrayList<UploadedFileInfoForm>();
        String path = ctx.getRealPath("resources\\files");
        for (String str : json) {
            UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(Integer.parseInt(str));
            InputStream fs = null;
            byte[] buffer = new byte[32];
            int count = 0;
            try{
                fs = new FileInputStream(path + File.separator + uploadedFileInfoForm.getPath());
                while((count = fs.read(buffer)) != -1){
                    if(buffer[0] != Integer.parseInt("A5", 16) && buffer[1] != Integer.parseInt("4C",16) ){
                        continue;
                    }else{

                    }
                }

            }catch(Exception e){
                System.out.println(e.toString());
            }
        }

    }
}
