package com.elstele.bill.filesWorkers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class FileDownloadWorker {
    private static final int BUFFER_SIZE = 4096;
    final static Logger log = LogManager.getLogger(FileDownloadWorker.class);

    @Autowired
    ServletContext ctx;

    public void doFileDownload(String path, String id, HttpServletResponse response) {
        String childPath = path + File.separator + id.substring(0, 7);
        String fullPath = childPath + File.separator + id;
        File downloadFile = new File(fullPath);
        log.info(fullPath);
        try {
            FileInputStream inputStream = new FileInputStream(downloadFile);
            mimeTypeDetermine(fullPath, response);
            response.setContentLength((int) downloadFile.length());
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            writeFileWithBuffer(inputStream, outStream);
            inputStream.close();
            outStream.close();
            log.info("File: " + downloadFile.getName() + "downloading is done");
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void doArchiveDownload(String path, String directoryName, HttpServletResponse response) {
        FileArchiveCreater fileArchiveCreater = new FileArchiveCreater();
        fileArchiveCreater.createArchive(path, directoryName);
        String zipFilePath = path + File.separator + directoryName + ".zip";
        File downloadFile = new File(zipFilePath);
        log.info(zipFilePath);
        try {
            FileInputStream inputStream = new FileInputStream(zipFilePath);
            mimeTypeDetermine(zipFilePath, response);
            response.setContentLength((int) downloadFile.length());
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"",
                    downloadFile.getName());
            response.setHeader(headerKey, headerValue);
            OutputStream outStream = response.getOutputStream();
            writeFileWithBuffer(inputStream, outStream);
            inputStream.close();
            outStream.close();
            log.info("Archive: " + downloadFile.getName() + "downloading is done");
        } catch (IOException e) {
            log.error(e);
        }
    }

    public void writeFileWithBuffer(InputStream inputStream, OutputStream outStream) {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        try {
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            log.error(e);
        }

    }

    public void mimeTypeDetermine(String path, HttpServletResponse response) {
        String mimeType = ctx.getMimeType(path);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        log.info("MIME type of the downloading file is: " + mimeType);

        response.setContentType(mimeType);
    }

}
