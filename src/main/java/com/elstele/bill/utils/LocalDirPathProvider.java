package com.elstele.bill.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;

import static com.elstele.bill.utils.Constants.PATH_TO_UPLOAD_FOLDER;
import static com.elstele.bill.utils.Constants.PATH_TO_CSV_FOLDER;


@Service
public class LocalDirPathProvider {

    @Autowired
    private PropertiesHelper propertiesHelper;
    @Autowired
    private ServletContext ctx;

    public String getKDFDirectoryPath() {
        String path = propertiesHelper.getKDFFilesDirectory();
        if (path == null) {
            path = ctx.getRealPath(PATH_TO_UPLOAD_FOLDER);
        }
        return path;
    }

    public String getCSVDirectoryPath() {
        String path = propertiesHelper.getCSVFilesDirectory();
        if (path == null) {
            path = ctx.getRealPath(PATH_TO_CSV_FOLDER);
        }
        return path;
    }

}