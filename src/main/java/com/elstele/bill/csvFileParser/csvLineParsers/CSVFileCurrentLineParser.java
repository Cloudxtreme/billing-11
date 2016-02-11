package com.elstele.bill.csvFileParser.csvLineParsers;

import com.elstele.bill.Builders.form.CallForCSVFFormBuilder;
import com.elstele.bill.form.CallForCSVForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Date;

public class CSVFileCurrentLineParser extends CSVFileLineParser {
    private CallForCSVFFormBuilder builder = new CallForCSVFFormBuilder();

    public CallForCSVForm fillFormFromLine(String line) {
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

            return builder.build()
                .withCallCostTotal(costWithNDS)
                .withDirDescrpOrg(dir_descr)
                .withDirPrefix(dir_prefix)
                .withDuration(Integer.parseInt(duration))
                .withNumberA(numberA)
                .withNumberB(numberB)
                .withProvider("2")
                .withStartTime(startTime)
                .getRes();
    }

}
