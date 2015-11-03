package com.elstele.bill.utils;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class FileArchiveCreater {

    private static final int BUFFER_SIZE = 4096;
    List<String> fileNameList = new ArrayList<String>();

    public void createArchive(String path, String directoryName) {
        String zipFilePath = path + File.separator + directoryName + ".zip";
        String fileDirectoryPath = path + File.separator + directoryName;
        createFileNameList(path, directoryName);
        zipItFiles(zipFilePath, fileDirectoryPath);

    }

    public void createFileNameList(String path, String directoryName) {
        File fileDirectory = new File(path + File.separator + directoryName);
        if (fileDirectory.isDirectory()) {
            File[] fileArray = fileDirectory.listFiles();
            assert fileArray != null;
            for (File file : fileArray) {
                if (file.isFile()) {
                    fileNameList.add(file.getName());
                }
            }
        }
    }

    public void zipItFiles(String zipFilePath, String fileDirectoryPath) {
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            System.out.println("Output to Zip : " + zipFilePath);
            for (String fileName : fileNameList) {
                System.out.println("File Added : " + fileName);
                ZipEntry ze = new ZipEntry(fileName);
                zos.putNextEntry(ze);
                FileInputStream in =
                        new FileInputStream(fileDirectoryPath + File.separator + fileName);
                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }
            zos.closeEntry();
            zos.close();
            System.out.println("Done");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }
}
