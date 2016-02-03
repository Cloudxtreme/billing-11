package com.elstele.bill.csvFileParser.csvLineParsers;

import com.elstele.bill.datasrv.interfaces.CallForCSVDataService;
import com.elstele.bill.form.CallForCSVForm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVFileUKRNETLineParser extends CSVFileLineParser {
    private CallForCSVDataService callForCSVDataService;

    public CSVFileUKRNETLineParser(CallForCSVDataService callForCSVDataService) {
        this.callForCSVDataService = callForCSVDataService;
    }

    public CallForCSVForm fillFormFromLine(String line) {
        Map<String, String> directionMap = new HashMap<>();
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
        callForCSVForm.setDuration(Integer.parseInt(duration));
        callForCSVForm.setDirPrefix(dir_prefix);
        callForCSVForm.setDirDescrpOrg(dir_descr);
        callForCSVForm.setProvider("1");

        return callForCSVForm;
    }
}
