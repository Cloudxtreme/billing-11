package com.elstele.bill.filesWorkers;

import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MultipartFileConverter {
    final static Logger LOGGER = LogManager.getLogger(MultipartFileConverter.class);

    public static File convert(MultipartFile file, LocalDirPathProvider pathProvider) {
        String path = pathProvider.getCSVDirectoryPath();
        File convFile = new File(path + File.separator + file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return convFile;
    }
}
