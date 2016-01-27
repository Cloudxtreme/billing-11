package com.elstele.bill.csvFileParser.csvLineParsers;

import com.elstele.bill.form.CallForCSVForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Date;

public class CSVFileCurrentLineParser extends CSVFileLineParser {
    final static Logger LOGGER = LogManager.getLogger(CSVFileCurrentLineParser.class);

    public CallForCSVForm fillFormByLine(String line) {
        try {
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
        }catch(ArrayIndexOutOfBoundsException e){
            LOGGER.error(e.getMessage());
            return null;
        }
    }

}
