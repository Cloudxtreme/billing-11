package com.elstele.bill.reportCreaters.creatersImpl;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreaters.reportInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.List;

@Service
public class LocalCallsMainReportCreaterImpl extends LocalCallsDetailReportCreaterImpl implements ReportCreaterInterface {

    @Autowired
    CallDataService callDataService;

    public void reportCreateMain(String path, String fileName, String year, String month) {
        PrintStream bw = createFileForWriting(path, fileName, year, month);
        filePrintingCreate(bw, year, month);
    }

    public void filePrintingCreate(PrintStream bw, String year, String month) {
        try {
            Double totalCallDuration = 0.0;
            List<String> listWithNumberA = getUniqueNumbersA(year, month);
            Integer strIndex = 0;
            for (String numberA : listWithNumberA) {
                List<Call> callsListByNumberA = getCallsFromDBByNumbersA(numberA, year, month);
                Double callDurationTotalForThisNumber = callDataPrint(bw, callsListByNumberA);
                strIndex++;
                String strForPrint = strIndex + " " + numberA.substring(1, numberA.length()) + " " + round(callDurationTotalForThisNumber, 2) + " секунд";
                bw.println(strForPrint);
                totalCallDuration += callDurationTotalForThisNumber;
            }
            String firstString = " Общая длительность переговоров- " + round(totalCallDuration, 2) + " секунд";
            bw.println(firstString);
            bw.close();
            log.info("Report generating is Done");
        } catch (Exception e) {
            log.error(e);
        }
    }

    public Double callDataPrint(PrintStream bw, List<Call> callListByNumberA) {
        Double callDurationTotalForThisNumber = 0.0;
        for (Call call : callListByNumberA) {
            callDurationTotalForThisNumber += call.getDuration();
        }
        return callDurationTotalForThisNumber;
    }
}
