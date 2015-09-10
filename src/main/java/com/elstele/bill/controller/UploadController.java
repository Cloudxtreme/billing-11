package com.elstele.bill.controller;

import com.elstele.bill.domain.UploadedFiles;
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
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;


@Controller
public class UploadController {

    @Autowired
    ServletContext ctx;

    @RequestMapping(value = "/uploadfile", method = RequestMethod.GET)
    public ModelAndView setPagetoUpload() {
        ModelAndView model = new ModelAndView("uploadKDF");

        return model;
    }


    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    @ResponseBody
    public String putFileToFolder(MultipartHttpServletRequest request, HttpServletResponse response, HttpServletRequest requestHttp) {
        String fileName = null;
        ctx = requestHttp.getSession().getServletContext();
        String path = ctx.getRealPath("resources\\files");

        Iterator<String> iter = request.getFileNames();
        MultipartFile multipartFile = null;
        while(iter.hasNext()) {
            multipartFile = request.getFile(iter.next());


            try {
                assert multipartFile != null;
                fileName = multipartFile.getContentType();
                if (fileName.equalsIgnoreCase("image/png")) {
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


        String path = ctx.getRealPath("resources\\files");
        File dir = new File(path);
        File[] files = dir.listFiles();

        ArrayList filPaths = new ArrayList();
        for (File file : files) {
            filPaths.add(file.getName());
        }

        ModelAndView model = new ModelAndView("uploadedfiles");
        model.addObject("uploadedList", filPaths);
        return model;
    }
}
