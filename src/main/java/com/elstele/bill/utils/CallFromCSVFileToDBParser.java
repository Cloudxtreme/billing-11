package com.elstele.bill.utils;


import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CallFromCSVFileToDBParser {

    final static Logger log = LogManager.getLogger(CallFromCSVFileToDBParser.class);

    @Autowired
    CallForCSVDataService callForCSVDataService;
    private Map<String, String> directionMap = new HashMap<>();

    public CallForCSVForm arrayHandlingMethodCSV(String line) {
        final String DELIMITER = ";";
        String[] data = line.split(DELIMITER);

        String numberA = data[0].substring(5, 12);
        String numberB = data[1];
        String duration = data[2];
        String call_start = data[3];
        String dir_prefix = data[4];
        String dir_descr_orig = data[5];
        String cost_without_nds = data[9];
        String dir_descr = dir_descr_orig.replace("'", "");
        dir_descr = dir_descr.replace("'", "\'");

        Date startTime = startTimeHandling(call_start);
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

    public CallForCSVForm arrayHandlingMethodCSVUkrNet(String line) {
        final String DELIMITER = " ";
        Pattern pattern = Pattern.compile("\\s{2,}");
        Matcher matcher = pattern.matcher(line);
        String result = matcher.replaceAll(" ");

        String[] data = result.split(DELIMITER);

        String numberA = data[0].substring(2, 9);
        String numberB = data[3];
        String duration = data[7];
        String call_start = data[1];
        String dir_prefix = data[4];
        String dir_descr = "";
        String cost_without_nds = data[9];
        if (checkWithRegExp(dir_prefix, "/^0.*/")) {
            dir_prefix = dir_prefix;
        }
        if (dir_prefix.startsWith("8")) {
            dir_prefix = "0" + dir_prefix;
        }
        if (checkWithRegExp(dir_prefix, "/^[1-7].*/")) {
            dir_prefix = "00" + dir_prefix;
        }

        dir_descr = directionMap.get(dir_prefix);
        if (dir_descr == null) {
            dir_descr = callForCSVDataService.getDescriptionFromDirections(dir_prefix);
            if (dir_descr != null) {
                directionMap.put(dir_prefix, dir_descr);
            } else {
                dir_descr = "no data";
            }
        }
        dir_descr = dir_descr.replace("'", "");
        dir_descr = dir_descr.replace("'", "\'");

        Date startTime = startTimeHandlingUkrNet(call_start);
        String costWithNDS = costWithNDS(cost_without_nds);

        CallForCSVForm callForCSVForm = new CallForCSVForm();
        callForCSVForm.setNumberA(numberA);
        callForCSVForm.setNumberB(numberB);
        callForCSVForm.setCostCallTotal(costWithNDS);
        callForCSVForm.setStartTime(startTime);
        callForCSVForm.setDuration(duration);
        callForCSVForm.setDirPrefix(dir_prefix);
        callForCSVForm.setDirDescrpOrg(dir_descr);
        callForCSVForm.setProvider("1");

        return callForCSVForm;
    }

    public Date startTimeHandling(String call_start) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String year = call_start.substring(6, 10);
        String month = call_start.substring(3, 5);
        String day = call_start.substring(0, 2);
        String time = call_start.substring(10, 18);
        Date startTime = new Date();
        try {
            startTime = sdf.parse(year + "-" + month + "-" + day + " " + time);
        } catch (ParseException e) {
            log.error(e);
        }
        return startTime;
    }

    public Date startTimeHandlingUkrNet(String call_start) {
        String year = call_start.substring(0, 4);
        String month = call_start.substring(4, 6);
        String day = call_start.substring(6, 8);
        String hour = call_start.substring(8, 10);
        String min = call_start.substring(10, 12);
        String sec = call_start.substring(12, 14);
        String startTimeStr = year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = new Date();
        try {
            startTime = sdf.parse(startTimeStr);
        } catch (ParseException e) {
            log.error(e);
        }
        return startTime;
    }


    public String costWithNDS(String costWithoutNDS) {
        if (costWithoutNDS.indexOf(",") == 0) {
            costWithoutNDS = "0" + costWithoutNDS;
        }
        return Double.toString(Double.parseDouble(costWithoutNDS) * 1.2);
    }

    public File convert(MultipartFile file) {
        File convFile = new File(file.getOriginalFilename());
        try {
            convFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
        } catch (IOException e) {
            log.error(e);
        }
        return convFile;
    }

    public boolean checkWithRegExp(String strToCheck, String strPattern) {
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strToCheck);
        return m.matches();
    }
}
