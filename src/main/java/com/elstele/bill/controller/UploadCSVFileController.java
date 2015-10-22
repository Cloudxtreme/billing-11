package com.elstele.bill.controller;

import com.elstele.bill.dao.CallForCSVDAO;
import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import com.elstele.bill.utils.CallForCSVHelper;
import com.elstele.bill.utils.ReportCreater;
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
import java.io.*;
import java.util.Iterator;
import java.util.Locale;

@Controller
public class UploadCSVFileController {
    @Autowired
    CallForCSVDAO callForCSVDAO;

    @Autowired
    ServletContext ctx;

    @Autowired
    CallForCSVDataService callForCSVDataService;

    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.GET)
    public ModelAndView fileCSVFirstView() {
        ModelAndView model = new ModelAndView("uploadCSVFile");
        return model;
    }

    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax fileCSVUploadSubmit(MultipartHttpServletRequest file, HttpServletRequest request, HttpServletResponse response, HttpServletRequest requestHttp) throws IOException {
        if(file != null) {
                BufferedReader fileReader = null;
                Iterator<String> iter = file.getFileNames();
                Locale.setDefault(new Locale("ru_RU.CP1251"));
                Locale locale2 = Locale.getDefault();
                while (iter.hasNext()) {
                    try {
                        MultipartFile multipartFile = file.getFile(iter.next());
                        File fileFromMulti = CallForCSVHelper.convert(multipartFile);
                        String fileName =  fileFromMulti.getName();
                        if(!fileName.contains("ukr")){
                            callForCSVDataService.clearReportTable();
                        }
                        String line = "";
                        fileReader = new BufferedReader(new FileReader(fileFromMulti));
                        int counter = 1;
                        boolean firstLine = true;
                        while ((line = fileReader.readLine()) != null) {
                            CallForCSVForm callForCSVForm = new CallForCSVForm();
                            counter++;
                            if(!fileName.contains("ukr")){

                                if (firstLine) {
                                    firstLine = false;
                                } else {
                                    callForCSVForm = CallForCSVHelper.arrayHandlingMethodCSV(line);
                                    callForCSVDataService.addReportData(callForCSVForm);
                                }
                            }else {
                                callForCSVForm = CallForCSVHelper.arrayHandlingMethodCSVUkrNet(line);
                                callForCSVDataService.addReportData(callForCSVForm);
                            }
                        }

                    } catch (Exception e) {
                        return ResponseToAjax.ERROR;
                    } finally {
                        try {
                            assert fileReader != null;
                            fileReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return ResponseToAjax.SUCCESS;
                }

        }
        return ResponseToAjax.FULLOPERATION;
    }

    @RequestMapping(value = "/reportCreating", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax generateAndDownloadReport(HttpServletRequest request, @RequestBody String[] json , HttpServletResponse response,  HttpServletRequest requestHttp) throws IOException {
        ctx = requestHttp.getSession().getServletContext();
        String path = ctx.getRealPath("resources\\files\\csvFiles");
        ReportCreater reportCreater = new ReportCreater();
        for (String reportName : json ) {
            reportCreater.callLongReportCreate(path, reportName, callForCSVDataService);
        }
        return ResponseToAjax.SUCCESS;

    }
}
