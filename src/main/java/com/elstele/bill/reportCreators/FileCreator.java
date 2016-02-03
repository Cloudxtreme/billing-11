package com.elstele.bill.reportCreators;

import com.elstele.bill.reportCreators.factory.ReportDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileCreator {
    final public static Logger LOGGER = LogManager.getLogger(FileCreator.class);

    public static PrintStream createFileForWriting(ReportDetails reportDetails) {
        createMainFolder(reportDetails.getPath());
        String pathDir = createFolderWithDate(reportDetails);
        File file = new File(pathDir + File.separator + reportDetails.getYear() + "-" + reportDetails.getMonth() + "_" + reportDetails.getReportName() + ".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, false)), true, "cp1251");
            LOGGER.info("File " + file.getName() + " is successful created for writing");
            return ps;
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public static void createMainFolder(String path) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            boolean fileMet = false;
            try {
                fileDir.mkdir();
                fileMet = true;
            } catch (SecurityException e) {
                LOGGER.error(e.getMessage(), e);
            }
            if (fileMet) {
                LOGGER.info("File dir " + fileDir.getAbsolutePath() + " is created");
            }
        }
    }

    public static String createFolderWithDate(ReportDetails reportDetails) {
        File directory = new File(reportDetails.getPath() + File.separator + reportDetails.getYear() + "-" + reportDetails.getMonth());
        if (!directory.exists()) {
            boolean fileMet = false;
            try {
                directory.mkdir();
                fileMet = true;
            } catch (SecurityException e) {
                LOGGER.error(e.getMessage(), e);
            }
            if (fileMet) {
                LOGGER.info("File directory " + directory.getAbsolutePath() + " is created");
            }
        }
        return directory.getPath();
    }
}
