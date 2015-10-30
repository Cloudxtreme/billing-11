package com.elstele.bill.controller;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import com.elstele.bill.utils.CallForCSVHelper;
import com.elstele.bill.utils.reportCreaters.*;
import com.elstele.bill.utils.ResponseToAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

@Controller
public class UploadCSVFileController {

    @Autowired
    ServletContext ctx;
    @Autowired
    CallForCSVDataService callForCSVDataService;
    @Autowired
    LongReportCreaterImpl longReportCreaterImpl;
    @Autowired
    LongReportVegaCreaterImpl longReportVegaCreater;
    @Autowired
    LongReportRACreaterImpl longReportRACreaterImpl;
    @Autowired
    LongReportRAUkrTelCreaterImpl longReportRAUkrTelCreater;
    @Autowired
    LongReportRAVegaCreaterImpl longReportRAVegaCreater;
    @Autowired
    ShortReportCreaterImpl shortReportCreater;
    @Autowired
    ShortReportRECreaterImpl shortReportRECreater;
    @Autowired
    ShortReportREUkrTelCreaterImpl shortReportREUkrTelCreater;
    @Autowired
    ShortReportREVegaCreaterImpl shortReportREVegaCreater;
    @Autowired
    ShortReportVegaCreaterImpl shortReportVegaCreater;

    @Qualifier("localCallsDetailReportCreaterImpl")
    @Autowired
    LocalCallsDetailReportCreaterImpl localCallsDetailReportCreater;

    @Autowired
    LocalCallsMainReportCreaterImpl localCallsMainReportCreater;
    @Autowired
    LocalCallsCostReportCreaterImpl localCallsCostReportCreater;

    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.GET)
    public ModelAndView fileCSVFirstView() {
        ModelAndView model = new ModelAndView("uploadCSVFile");
        String path = ctx.getRealPath("resources\\files\\csvFiles");
        model.addObject("filesList", getFileList(path));
        return model;
    }

    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax fileCSVUploadSubmit(MultipartHttpServletRequest file, HttpServletRequest request, HttpServletResponse response, HttpServletRequest requestHttp) throws IOException {
        if (file != null) {
            BufferedReader fileReader = null;
            Iterator<String> iter = file.getFileNames();
            Locale.setDefault(new Locale("ru_RU.CP1251"));
            Locale locale2 = Locale.getDefault();
            while (iter.hasNext()) {
                try {
                    MultipartFile multipartFile = file.getFile(iter.next());
                    File fileFromMulti = CallForCSVHelper.convert(multipartFile);
                    String fileName = fileFromMulti.getName();
                    if (!fileName.contains("ukr")) {
                        callForCSVDataService.clearReportTable();
                    }
                    String line = "";
                    fileReader = new BufferedReader(new FileReader(fileFromMulti));
                    int counter = 1;
                    boolean firstLine = true;
                    while ((line = fileReader.readLine()) != null) {
                        CallForCSVForm callForCSVForm = new CallForCSVForm();
                        counter++;
                        if (!fileName.contains("ukr")) {
                            if (firstLine) {
                                firstLine = false;
                            } else {
                                callForCSVForm = CallForCSVHelper.arrayHandlingMethodCSV(line);
                                callForCSVDataService.addReportData(callForCSVForm);
                            }
                        } else {
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
    public ResponseToAjax generateAndDownloadReport(@RequestBody String[] json, HttpServletRequest requestHttp) throws IOException {
        ctx = requestHttp.getSession().getServletContext();
        String path = ctx.getRealPath("resources\\files\\csvFiles");
        for (String reportName : json) {
            if (reportName.equalsIgnoreCase("longReport")) {
                longReportCreaterImpl.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("longReportVega")) {
                longReportVegaCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("longReportRA")) {
                longReportRACreaterImpl.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("longReportRAUkrTel")) {
                longReportRAUkrTelCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("longReportRAVega")) {
                longReportRAVegaCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("shortReport")) {
                shortReportCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("shortReportRE")) {
                shortReportRECreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("shortReportREUkrTel")) {
                shortReportREUkrTelCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("shortReportREVega")) {
                shortReportREVegaCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("shortReportVega")) {
                shortReportVegaCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("localCallsDetailReport")) {
                localCallsDetailReportCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("localCallsMainReport")) {
                localCallsMainReportCreater.reportCreateMain(path, reportName);
            }
            if (reportName.equalsIgnoreCase("localCallsCostReport")) {
                localCallsCostReportCreater.reportCreateMain(path, reportName);
            }
        }
        return ResponseToAjax.SUCCESS;

    }


    public List<String> getFileList(String path) {
        File fileDir = new File(path);
        File[] filesArr = fileDir.listFiles();
        List<String> filesNameList = new ArrayList<String>();
        for (int i = 0; i < filesArr.length; i++) {
            if (filesArr[i].isFile()) {
                try {
                    filesNameList.add(filesArr[i].getName());
                    System.out.println("File " + filesArr[i].getName() + " is added to List");
                } catch (SecurityException e) {
                    System.out.println(e.toString());
                }
            } else if (filesArr[i].isDirectory()) {
                File[] fileArrWithChildDirectory = filesArr[i].listFiles();
                System.out.println(filesArr[i].getName() + " is Directory");
                assert fileArrWithChildDirectory != null;
                for (int j = 0; j < fileArrWithChildDirectory.length; j++) {
                    if (fileArrWithChildDirectory[j].isFile()) {
                        try {
                            filesNameList.add(fileArrWithChildDirectory[j].getName());
                            System.out.println(fileArrWithChildDirectory[j].getName() + " is added to List");
                        } catch (SecurityException e) {
                            System.out.println(e.toString());
                        }
                    }

                }

            }
        }
        return filesNameList;
    }
}
