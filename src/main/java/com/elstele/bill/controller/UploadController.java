package com.elstele.bill.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sun.net.ProgressEvent;
import sun.net.ProgressListener;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


@Controller
public class UploadController {

    @Autowired
    ServletContext ctx;

    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public ModelAndView setPagetoUpload(){
        ModelAndView model = new ModelAndView("uploadKDF");

        return model;
    }

    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public @ResponseBody String putFileToFolder(@RequestParam("file") MultipartFile[] files, HttpServletRequest request){
        String fileName = null;
        ctx = request.getSession().getServletContext();
        String path = ctx.getRealPath("resources\\files");

        if (files != null && files.length > 0) {
            for(int i = 0; i < files.length; i++) {
                try {
                    fileName = files[i].getContentType();
                    if (fileName.equalsIgnoreCase("image/png")){
                        byte[] bytes = files[i].getBytes();
                        File uploadDir = new File("D:\\FolderForFile");
                        String originalName = files[i].getOriginalFilename();
                        BufferedOutputStream buffStream =
                                new BufferedOutputStream(new FileOutputStream(path + File.separator +originalName));
                        buffStream.write(bytes);


                        /*ProgressListener progressListener = new ProgressListener() {
                            public void progressStart(ProgressEvent progressEvent) {

                            }

                            public void progressUpdate(ProgressEvent progressEvent) {

                            }

                            public void progressFinish(ProgressEvent progressEvent) {

                            }
                        };*/

                        buffStream.close();
                    } else {
                        return "File type is not png";
                    }



                } catch (Exception e) {
                    return "You failed to upload " + files[i].getName() + ": " + e.getMessage() +"<br/>";
                }
            }
            return "";
        } else {
            return "Unable to upload. File is empty.";
        }
    }
}
