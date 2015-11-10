package com.elstele.bill.controller;

import com.elstele.bill.datasrv.CSVParserDataService;
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
    CSVParserDataService csvParserDataService;



    @RequestMapping(value = "/uploadCSVFile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax fileCSVUploadSubmit(MultipartHttpServletRequest multiPartHTTPServletRequestFiles) {
     return csvParserDataService.parse(multiPartHTTPServletRequestFiles);
    }

    @RequestMapping(value = "/uploadCSVFile/generateFileTree", method = RequestMethod.POST)
    @ResponseBody
    public FileDirTreeGeneraterForm[] generateFileTree() {
        path = ctx.getRealPath("resources\\files\\csvFiles");
        FileTreeGenerater fileTreeGenerater = new FileTreeGenerater();
        return fileTreeGenerater.getFileTreeArray(path);
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
