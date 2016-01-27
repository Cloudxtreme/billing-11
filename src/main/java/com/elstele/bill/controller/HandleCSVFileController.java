package com.elstele.bill.controller;

import com.elstele.bill.datasrv.interfaces.CSVFileDataService;
import com.elstele.bill.datasrv.interfaces.ReportDataService;
import com.elstele.bill.filesWorkers.FileDownloadWorker;
import com.elstele.bill.filesWorkers.FileTreeGenerater;
import com.elstele.bill.form.FileDirTreeGeneraterForm;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.exceptions.IncorrectReportNameException;
import com.elstele.bill.exceptions.ResourceNotFoundException;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class HandleCSVFileController {

    private String path;
    @Autowired
    ServletContext ctx;
    @Autowired
    FileDownloadWorker fileDownloadWorker;
    @Autowired
    ReportDataService reportDataService;
    @Autowired
    CSVFileDataService csvFileDataService;
    @Autowired
    LocalDirPathProvider pathProvider;


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException() {
        return "404";
    }


    @RequestMapping(value = "/uploadcsvfile", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax fileCSVUploadSubmit(@RequestParam(value = "flag") String selectedFileType, MultipartHttpServletRequest multiPartHTTPServletRequestFiles) {
        return csvFileDataService.handle(multiPartHTTPServletRequestFiles, selectedFileType);
    }

    @RequestMapping(value = "/uploadcsvfile/generateFileTree", method = RequestMethod.POST)
    @ResponseBody
    public FileDirTreeGeneraterForm[] generateFileTree() {
        path = pathProvider.getCSVDirectoryPath();
        FileTreeGenerater fileTreeGenerater = new FileTreeGenerater();
        return fileTreeGenerater.getFileTreeArray(path);
    }

    @RequestMapping(value = "/reportCreating", method = RequestMethod.POST)
    @ResponseBody
    public ResponseToAjax generateReport(@RequestBody String[] json) throws IOException, IncorrectReportNameException {
        if(json == null)throw  new ResourceNotFoundException();
        return  reportDataService.createReport(json);
    }

    @RequestMapping(value = "downloadFile", method = RequestMethod.GET)
    public void doDownload(HttpServletResponse response,
                           @RequestParam(value = "fileId") String id) throws IOException {
        path = pathProvider.getCSVDirectoryPath();
        fileDownloadWorker.doFileDownload(path, id, response);
    }

    @RequestMapping(value = "downloadZIP", method = RequestMethod.GET)
    public void doDownloadZIP(HttpServletResponse response,
                              @RequestParam(value = "directoryName") String directoryName) throws IOException {
        path = pathProvider.getCSVDirectoryPath();
        fileDownloadWorker.doArchiveDownload(path, directoryName, response);
    }

}
