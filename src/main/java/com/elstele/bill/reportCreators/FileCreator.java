package com.elstele.bill.reportCreators;

import com.elstele.bill.reportCreators.factory.ReportDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class FileCreator {
    final public static Logger log = LogManager.getLogger(FileCreator.class);

    public PrintStream createFileForWriting(ReportDetails reportDetails) {
        createMainFolder(reportDetails.getPath());
        String pathDir = createFolderWithDate(reportDetails.getPath(), reportDetails.getYear(), reportDetails.getMonth());
        File file = new File(pathDir + File.separator + reportDetails.getYear() + "-" + reportDetails.getMonth() + "_" + reportDetails.getReportName() + ".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            PrintStream bw = new PrintStream(new BufferedOutputStream(new FileOutputStream(file, false)), true, "cp1251");
            log.info("File " + file.getName() + " is successful created for writing");
            return bw;
        } catch (IOException e) {
            log.error(e);
            return null;
        }
    }

    public void createMainFolder(String path) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            boolean fileMet = false;
            try {
                fileDir.mkdir();
                fileMet = true;
            } catch (SecurityException e) {
                log.error(e);
            }
            if (fileMet) {
                log.info("File dir " + fileDir.getAbsolutePath() + " is created");
            }
        }
    }

    public String createFolderWithDate(String path, String year, String month) {
        File directory = new File(path + File.separator + year + "-" + month);
        if (!directory.exists()) {
            boolean fileMet = false;
            try {
                directory.mkdir();
                fileMet = true;
            } catch (SecurityException e) {
                log.error(e);
            }
            if (fileMet) {
                log.info("File directory " + directory.getAbsolutePath() + " is created");
            }
        }
        return directory.getPath();
    }
}
