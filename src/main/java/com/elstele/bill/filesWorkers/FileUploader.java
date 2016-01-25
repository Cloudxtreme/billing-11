package com.elstele.bill.filesWorkers;

import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.utils.Enums.FileStatus;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class FileUploader {
    @Autowired
    UploadedFileInfoDataService dataService;

    @Autowired
    LocalDirPathProvider pathProvider;

    final static Logger LOGGER = LogManager.getLogger(FileUploader.class);
    private String path;

    public ResponseToAjax uploadFileToServer(MultipartHttpServletRequest request) {
        path = pathProvider.getKDFDirectoryPath();
        Iterator<String> iter = request.getFileNames();
        while (iter.hasNext()) {
            MultipartFile multipartFile = request.getFile(iter.next());
            String fileExtension = multipartFile.getContentType();
            if (fileExtension.equalsIgnoreCase("application/octet-stream")) {
                try {
                    writeFileOnServer(multipartFile);
                    addFileInfoToDB(multipartFile);
                } catch (IOException e) {
                    LOGGER.error(e.toString());
                    return ResponseToAjax.ERROR;
                }
            } else {
                return ResponseToAjax.INCORRECTTYPE;
            }
        }
        return ResponseToAjax.SUCCESS;
    }


    private void addFileInfoToDB(MultipartFile multipartFile) {
        UploadedFileInfoForm uploadedFileInfoForm = new UploadedFileInfoForm();
        uploadedFileInfoForm.setPath(multipartFile.getOriginalFilename());
        uploadedFileInfoForm.setFileName(multipartFile.getOriginalFilename());
        uploadedFileInfoForm.setFileSize(multipartFile.getSize());
        uploadedFileInfoForm.setFileStatus(FileStatus.NEW);
        dataService.createOrUpdateFileInfo(uploadedFileInfoForm);
    }

    private void writeFileOnServer(MultipartFile multipartFile) throws IOException {
        byte[] bytes = multipartFile.getBytes();
        String originalName = multipartFile.getOriginalFilename();
        BufferedOutputStream buffStream =
                new BufferedOutputStream(new FileOutputStream(path + File.separator + originalName));
        buffStream.write(bytes);
        buffStream.close();
    }

}
