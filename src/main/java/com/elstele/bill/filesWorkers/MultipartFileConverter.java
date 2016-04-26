package com.elstele.bill.filesWorkers;

import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class MultipartFileConverter {
    final static Logger LOGGER = LogManager.getLogger(MultipartFileConverter.class);
    private LocalDirPathProvider pathProvider;

    public MultipartFileConverter(LocalDirPathProvider pathProvider) {
        this.pathProvider = pathProvider;
    }

    public static File convert(MultipartFile file, String path) {
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdir();
        }
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

    public File convertAndUploadTariffFile(MultipartHttpServletRequest multiPartHTTPServletRequestFiles){
        Iterator<String> fileIterator = multiPartHTTPServletRequestFiles.getFileNames();
        MultipartFile multipartFile = multiPartHTTPServletRequestFiles.getFile(fileIterator.next());
        String path = pathProvider.getDOCXDirectoryPath();
        File file = MultipartFileConverter.convert(multipartFile, path);
        LOGGER.info("Tariff File name to handleTariffFile is : " + file.getName());
        return file;
    }
}
