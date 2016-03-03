package com.elstele.bill.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Properties;

@Service
public class PropertiesHelper {

    final static Logger LOGGER = LogManager.getLogger(PropertiesHelper.class);
    private Properties properties = null;
    private final static String KDF_UPLOAD_DIR = "kdfUploadFileDir";
    private final static String CSV_UPLOAD_DIR = "csvUploadFileDir";
    private final static String DOCX_UPLOAD_DIR = "docXUploadFileDir";

    private void init(){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        properties = new Properties();
        try {
            properties.load(classLoader.getResourceAsStream("bill_app.properties"));
        } catch (IOException e) {
            LOGGER.log(Level.ERROR, "Exceptionduring loading properties file bill_app.properties" + e.getMessage(), e);
        }
    }

    public String getKDFFilesDirectory(){
        if (properties == null){
            this.init();
        }
        return properties.getProperty(KDF_UPLOAD_DIR);
    }

    public String getCSVFilesDirectory(){
        if (properties == null){
            this.init();
        }
        return properties.getProperty(CSV_UPLOAD_DIR);
    }

    public String getDOCXDirectory(){
        if (properties == null){
            this.init();
        }
        return properties.getProperty(DOCX_UPLOAD_DIR);
    }
}
