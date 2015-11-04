package com.elstele.bill.controller;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import com.elstele.bill.form.FileDirTreeGeneraterForm;
import com.elstele.bill.utils.*;
import com.elstele.bill.utils.reportCreaters.*;
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
import java.util.Iterator;
import java.util.Locale;

@Controller
public class UploadCSVFileController {

    String path = "";
    @Autowired
    CallFromCSVFileToDBParser callFromCSVFileToDBParser;
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
    @Autowired
    FileDownloadWorker fileDownloadWorker;

    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.GET)
    public ModelAndView fileCSVFirstView() {
        ModelAndView model = new ModelAndView("uploadCSVFile");
        return model;
    }

    @RequestMapping(value = "/uploadCSVFile/generateFileTree", method = RequestMethod.POST)
    @ResponseBody
    public FileDirTreeGeneraterForm[] generateFileTree() {
        path = ctx.getRealPath("resources\\files\\csvFiles");
        FileTreeGenerater fileTreeGenerater = new FileTreeGenerater();
        FileDirTreeGeneraterForm[] fileDirTreeGeneraterForms = fileTreeGenerater.getFileTreeArray(path);
        return fileDirTreeGeneraterForms;
    }

    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax fileCSVUploadSubmit(MultipartHttpServletRequest file) {
        if (file != null) {
            BufferedReader fileReader = null;
            Iterator<String> iter = file.getFileNames();
            Locale.setDefault(new Locale("ru_RU.CP1251"));
            Locale locale2 = Locale.getDefault();
            while (iter.hasNext()) {
                try {
                    MultipartFile multipartFile = file.getFile(iter.next());
                    File fileFromMulti = callFromCSVFileToDBParser.convert(multipartFile);
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
                                callForCSVForm = callFromCSVFileToDBParser.arrayHandlingMethodCSV(line);
                                callForCSVDataService.addReportData(callForCSVForm);
                            }
                        } else {
                            callForCSVForm = callFromCSVFileToDBParser.arrayHandlingMethodCSVUkrNet(line);
                            callForCSVDataService.addReportData(callForCSVForm);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
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
    public ResponseToAjax generateAndDownloadReport(@RequestBody String[] json) throws IOException {
        path = ctx.getRealPath("resources\\files\\csvFiles");
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

    @RequestMapping(value = "downloadFile", method = RequestMethod.GET)
    public void doDownload(HttpServletResponse response,
                           @RequestParam(value = "fileId") String id) throws IOException {
        path = ctx.getRealPath("resources\\files\\csvFiles");
        fileDownloadWorker.doFileDownload(path, id, response);
    }

    @RequestMapping(value = "downloadZIP", method = RequestMethod.GET)
    public void doDownloadZIP(HttpServletResponse response,
                              @RequestParam(value = "directoryName") String directoryName) throws IOException {
        path = ctx.getRealPath("resources\\files\\csvFiles");
        fileDownloadWorker.doArchiveDownload(path, directoryName, response);
    }

}
