package com.elstele.bill.controller;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.datasrv.ReportDataService;
import com.elstele.bill.filesWorkers.FileDownloadWorker;
import com.elstele.bill.filesWorkers.FileTreeGenerater;
import com.elstele.bill.form.CallForCSVForm;
import com.elstele.bill.form.FileDirTreeGeneraterForm;
import com.elstele.bill.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.QueryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.Locale;

@Controller
public class HandleCSVFileController {

    String path;
    @Autowired
    ServletContext ctx;
    @Autowired
    FileDownloadWorker fileDownloadWorker;
    @Autowired
    ReportDataService reportDataService;
    @Autowired
    CallFromCSVFileToDBParser callFromCSVFileToDBParser;
    @Autowired
    CallForCSVDataService callForCSVDataService;
    final static Logger log = LogManager.getLogger(FileUploadController.class);


    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax fileCSVUploadSubmit(MultipartHttpServletRequest multiPartHTTPServletRequestFiles) {
        if (multiPartHTTPServletRequestFiles != null) {
            BufferedReader fileReader = null;
            Iterator<String> iter = multiPartHTTPServletRequestFiles.getFileNames();
            Locale.setDefault(new Locale("ru_RU.CP1251"));
            while (iter.hasNext()) {
                try {
                    MultipartFile multipartFile = multiPartHTTPServletRequestFiles.getFile(iter.next());
                    File fileFromMulti = callFromCSVFileToDBParser.convert(multipartFile);
                    String fileName = fileFromMulti.getName();
                    if (!fileName.contains("ukr")) {
                        try {
                            callForCSVDataService.clearReportTable();
                            log.info("Table cleared");
                        } catch (QueryException e) {
                            log.warn(e);
                        }
                    }
                    String line;
                    fileReader = new BufferedReader(new FileReader(fileFromMulti));
                    boolean firstLine = true;
                    while ((line = fileReader.readLine()) != null) {
                        CallForCSVForm callForCSVForm;
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
                    log.error(e);
                    return ResponseToAjax.ERROR;
                } finally {
                    try {
                        assert fileReader != null;
                        fileReader.close();
                    } catch (IOException e) {
                        log.error(e);
                    }
                }
                return ResponseToAjax.SUCCESS;
            }

        }
        return ResponseToAjax.FULLOPERATION;
    }

    @RequestMapping(value = "/uploadCSVFile/generateFileTree", method = RequestMethod.POST)
    @ResponseBody
    public FileDirTreeGeneraterForm[] generateFileTree() {
        path = ctx.getRealPath("resources\\files\\csvFiles");
        FileTreeGenerater fileTreeGenerater = new FileTreeGenerater();
        FileDirTreeGeneraterForm[] fileDirTreeGeneraterForms = fileTreeGenerater.getFileTreeArray(path);
        return fileDirTreeGeneraterForms;
    }

    @RequestMapping(value = "/reportCreating", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax generateAndDownloadReport(@RequestBody String[] json) throws IOException {
        return  reportDataService.createReport(json);
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
