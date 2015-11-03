package com.elstele.bill.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class FileDownloadWorker {
    private static final int BUFFER_SIZE = 4096;
    @Autowired
    ServletContext ctx;
     public void doFileDownload(String path, String id, HttpServletResponse response) throws IOException {
        String childPath = path + File.separator + id.substring(0, 7);
        String fullPath = childPath + File.separator + id;
        File downloadFile = new File(fullPath);
        System.out.println(fullPath);
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
    }

    public void doArchiveDownload(String path, String directoryName, HttpServletResponse response) throws IOException {
        FileArchiveCreater fileArchiveCreater = new FileArchiveCreater();
        fileArchiveCreater.createArchive(path, directoryName);
        String zipFilePath = path + File.separator + directoryName + ".zip";
        File downloadFile = new File(zipFilePath);
        System.out.println(zipFilePath);
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
    }

    public void writeFileWithBuffer(InputStream inputStream, OutputStream outStream) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = -1;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

    }

    public void mimeTypeDetermine(String path, HttpServletResponse response) {
        String mimeType = ctx.getMimeType(path);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);

        response.setContentType(mimeType);
    }

}
