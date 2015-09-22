package com.elstele.bill.controller;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.datasrv.UploadedFileInfoDataService;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.FileStatus;
import javafx.scene.control.ProgressBar;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Controller
public class UploadController {

    @Autowired
    ServletContext ctx;

    @Autowired
    UploadedFileInfoDataService uploadedFileInfoDataService;

    @Autowired
    CallDataService callDataService;

    float progress;

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
        progress = 0;
        return model;
    }



    @RequestMapping(value="/uploadedfiles/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteDevice(@RequestBody String json, HttpSession session, HttpServletResponse response, HttpServletRequest request){
        Integer id = Integer.parseInt(json);
        UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(id);
        String path = ctx.getRealPath("resources\\files");
        File file = new File(path + File.separator + uploadedFileInfoForm.getPath());
        Path filePath = file.toPath();
        String result = "";
        uploadedFileInfoDataService.setUploadedFileInfoStatus(id);
        try{
            Files.delete(filePath);
            result = "success";
        }catch(IOException e){
            System.out.println(e);
            return e.toString();
        }
        return result;
    }

    @RequestMapping(value = "/uploadedfiles/handle", method = RequestMethod.POST)
    @ResponseBody
    public void handleFiles(@RequestBody String[] json, HttpSession session, HttpServletResponse response, HttpServletRequest request) {
        List<UploadedFileInfoForm> list = new ArrayList<UploadedFileInfoForm>();
        String path = ctx.getRealPath("resources\\files");
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        for (String str : json) {
            UploadedFileInfoForm uploadedFileInfoForm = uploadedFileInfoDataService.getById(Integer.parseInt(str));
            InputStream fs = null;
            byte[] buffer = new byte[32];
            int count = 0;
            int len;
            long total= 0;
            try {
                fs = new FileInputStream(path + File.separator + uploadedFileInfoForm.getPath());
                do {
                    len = fs.read(buffer);

                    char[] hexChars = new char[buffer.length * 2];
                    for (int j = 0; j < buffer.length; j++) {
                        int v = buffer[j] & 0xFF;
                        hexChars[j * 2] = hexArray[v >>> 4];
                        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
                    }
                    String tempStrHEX = new String(hexChars);

                    if (tempStrHEX.startsWith("A54C")) {
                        Integer aon = Character.getNumericValue(tempStrHEX.charAt(4)) ;
                        String numberA = tempStrHEX.substring(5,12);
                        String numberB = tempStrHEX.substring(20,38).replaceAll("[^0-9]", "");
                        String startTime = tempStrHEX.substring(46,48)+ "." +tempStrHEX.substring(48,50)+ "-" + tempStrHEX.substring(12, 14)+":"+tempStrHEX.substring(14,16);
                        Long duration = Long.parseLong((tempStrHEX.substring(52, 54) + tempStrHEX.substring(16, 20)),16);
                        String dvoCodeA = tempStrHEX.substring(42,44);
                        String dvoCodeB = tempStrHEX.substring(44,46);

                        CallForm callForm = new CallForm();
                        callForm.setAonKat(aon);;
                        callForm.setNumberA(numberA);
                        callForm.setNumberB(numberB);
                        callForm.setDuration(duration);
                        callForm.setDvoCodeA(dvoCodeA);
                        callForm.setDvoCodeB(dvoCodeB);
                        callForm.setStartTime(startTime);
                        callDataService.addCalls(callForm);


                    }
                    count++;

                    progress = (((count*32)/(float)(uploadedFileInfoForm.getFileSize()*1.0))*100);



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

    @RequestMapping(value ="/uploadedfiles/handle/getprogress")
    public @ResponseBody Float getProgress(){
        return progress;
    }
}
