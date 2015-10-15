package com.elstele.bill.utils;

import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.*;
import java.util.Iterator;
import java.util.Locale;

public class CallForCSVHelper {


    public static CallForCSVForm arrayHandlingMethod(String line) {
        final String DELIMITER = ";";
        String[] data = line.split(DELIMITER);

        String numberA = data[0];
        String numberB = data[1];
        String duration = data[2];
        String call_start = data[3];
        String dir_prefix = data[4];
        String dir_descr_orig = data[5];
        String cost_without_nds = data[9];

        String dir_descr = dir_descr_orig.replace("'", "");
        dir_descr = dir_descr.replace("'", "\'");

        String startTime = startTimeHandling(call_start);
        String costWithNDS = costWithNDS(cost_without_nds);


        CallForCSVForm callForCSVForm = new CallForCSVForm();
        callForCSVForm.setNumberA(numberA);
        callForCSVForm.setNumberB(numberB);
        callForCSVForm.setCostCallTotal(costWithNDS);
        callForCSVForm.setStartTime(startTime);
        callForCSVForm.setDuration(duration);
        callForCSVForm.setDirPrefix(dir_prefix);
        callForCSVForm.setDirDescrpOrg(dir_descr);
        callForCSVForm.setProvider("2");

        return callForCSVForm;
    }

    public static String startTimeHandling(String call_start){
        String listStart[] = call_start.split(" ");
        String listDate[] = listStart[0].split("\\.");
        String startTime = listDate[2] + "-" + listDate[1] + "-" + listDate[0] + " " + listStart[1];
        return startTime;
    }

    public static String costWithNDS(String costWithoutNDS){
        if (costWithoutNDS.indexOf(",") == 0) {
            costWithoutNDS = "0" + costWithoutNDS;
        }
        return Double.toString(Double.parseDouble(costWithoutNDS) * 1.2);
    }

    public static File convert(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}
