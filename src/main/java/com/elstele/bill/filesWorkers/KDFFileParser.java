package com.elstele.bill.filesWorkers;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.datasrv.interfaces.UploadedFileInfoDataService;
import com.elstele.bill.form.CallForm;
import com.elstele.bill.form.UploadedFileInfoForm;
import com.elstele.bill.usersDataStorage.UserStateStorage;
import com.elstele.bill.utils.Enums.FileStatus;
import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.LocalDirPathProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class KDFFileParser {

    @Autowired
    UploadedFileInfoDataService fileDataService;
    @Autowired
    private LocalDirPathProvider pathProvider;
    @Autowired
    CallDataService callDataService;

    final static Integer BUFFER_SIZE = 32;
    final static Logger LOGGER = LogManager.getLogger(KDFFileParser.class);

    private String aon;
    private String numberA;
    private String numberB;
    private String dvoCodeA;
    private String dvoCodeB;
    private int duration;
    private String vkNum;
    private String ikNum;
    private String inputTrunk;
    private String outputTrunk;

    private String startTimeHour;
    private String startTimeMinutes;
    private String startMonth;
    private String startDate;
    private String calling;
    private String called;


    public ResponseToAjax parse(String[] selectedFileID, HttpSession session) {
        if (UserStateStorage.checkForBusy()) {
            return ResponseToAjax.BUSY;
        }
        try {
            char[] hexArray = "0123456789ABCDEF".toCharArray();
            long fullFilesSize = deductFullFilesSize(selectedFileID);
            int count = 0;
            float progress;

            for (String fileId : selectedFileID) {
                UploadedFileInfoForm uploadedFileInfoForm = fileDataService.getById(Integer.parseInt(fileId));
                byte[] buffer = new byte[BUFFER_SIZE];
                int len;
                InputStream fs = new FileInputStream(pathProvider.getKDFDirectoryPath() + File.separator + uploadedFileInfoForm.getPath());
                String flagString = "";
                LOGGER.info("KDF file: " + uploadedFileInfoForm.getPath());

                String yearFromFileName = "20" + uploadedFileInfoForm.getPath().substring(0, 2);
                String monthFromFileName = uploadedFileInfoForm.getPath().substring(3, 5);

                do {
                    len = fs.read(buffer);
                    char[] hexChars = new char[buffer.length * 2];
                    for (int j = 0; j < buffer.length; j++) {
                        int v = buffer[j] & 0xFF;
                        hexChars[j * 2] = hexArray[v >>> 4];
                        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
                    }
                    String tempStrHEX = new String(hexChars);
                    numberA = tempStrHEX.substring(5, 12);
                    if (tempStrHEX.startsWith("A54C") && !numberA.equalsIgnoreCase("0000000")) {
                        flagString = tempStrHEX;
                    }
                    if (tempStrHEX.startsWith("A54C") && numberA.equalsIgnoreCase("0000000")) {
                        getDataFromHaxString(flagString, tempStrHEX);
                        yearFromFileName = correctForNewYear(monthFromFileName, yearFromFileName);
                        determineCaller();
                        setDataToObject(yearFromFileName);
                    }
                    count++;
                    progress = (((count * BUFFER_SIZE) / (float) (fullFilesSize * 1.0)) * 100);
                    UserStateStorage.setProgressToObjectInMap(session, progress);
                } while (len != -1);
                fs.close();
                UserStateStorage.setProgressToObjectInMap(session, 100);
                uploadedFileInfoForm.setFileStatus(FileStatus.PROCESSED);
                fileDataService.updateFile(uploadedFileInfoForm);
            }
            return ResponseToAjax.SUCCESS;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResponseToAjax.ERROR;
        }
    }

    private void getDataFromHaxString(String flagString, String tempStrHEX) {
        aon = Character.toString(flagString.charAt(4));
        numberA = flagString.substring(5, 12);
        numberB = tempStrHEX.substring(20, 38).replaceAll("[^0-9]", "");
        dvoCodeA = flagString.substring(42, 44);
        dvoCodeB = flagString.substring(44, 46);
        duration = Integer.parseInt((tempStrHEX.substring(52, 54) + tempStrHEX.substring(16, 20)), 16);
        vkNum = tempStrHEX.substring(46, 49);
        ikNum = tempStrHEX.substring(49, 52);
        inputTrunk = tempStrHEX.substring(42, 44);
        outputTrunk = tempStrHEX.substring(44, 46);

        startTimeHour = flagString.substring(12, 14);
        startTimeMinutes = flagString.substring(14, 16);
        startMonth = flagString.substring(48, 50);
        startDate = flagString.substring(46, 48);
    }

    private long deductFullFilesSize(String[] selectedFileID){
        long fullFilesSize = 0;
        for (String fileId : selectedFileID) {
            UploadedFileInfoForm uploadedFileInfoForm = fileDataService.getById(Integer.parseInt(fileId));
            fullFilesSize += uploadedFileInfoForm.getFileSize();
        }
        return fullFilesSize;
    }


    private String correctForNewYear(String monthFromFileName, String yearFromFileName) {
        if ((startMonth.equalsIgnoreCase("12")) && (monthFromFileName.equalsIgnoreCase("01"))) {
            int yearInt = Integer.parseInt(yearFromFileName);
            yearInt = yearInt - 1;
            yearFromFileName = Integer.toString(yearInt);
        }
        return yearFromFileName;
    }

    private void determineCaller() {
        if (aon.equalsIgnoreCase("f")) {
            calling = numberB;
            called = numberA;
        } else {
            calling = numberA;
            called = numberB;
        }
    }

    private void setDataToObject(String yearFromFileName) {
        CallForm callForm = new CallForm();
        callForm.setAonKat(aon);
        callForm.setNumberA(calling);
        callForm.setNumberB(called);
        callForm.setDuration(duration);
        callForm.setDvoCodeA(dvoCodeA);
        callForm.setDvoCodeB(dvoCodeB);
        callForm.setStartTime(parseDate(yearFromFileName));
        callForm.setIkNum(ikNum);
        callForm.setVkNum(vkNum);
        callForm.setInputTrunk(inputTrunk);
        callForm.setOutputTrunk(outputTrunk);
        callDataService.addCalls(callForm);
    }

    private Date parseDate(String yearFromFileName) {
        try {
            String startTime = yearFromFileName + "/" + startMonth + "/" + startDate + " " + startTimeHour + ":" + startTimeMinutes + ":01";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            return simpleDateFormat.parse(startTime);
        } catch (ParseException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

}