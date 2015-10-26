package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LocalCallsMainReportCreaterImpl extends LocalCallsDetailReportCreaterImpl implements ReportCreaterInterface {

    @Autowired
    CallDataService callDataService;

    public void reportCreateMain(String path, String fileName) throws IOException {
        try {
            PrintStream bw = createFileForWriting(path, fileName);
            filePrintingCreate(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void filePrintingCreate(PrintStream bw) {
        Double totalCallDuration = 0.0;
        List<String> listWithNumberA = getUniqueNumbersA();
        Integer strIndex = 0;
        for (String numberA : listWithNumberA) {
            List<Call> callsListByNumberA = getCallsFromDBByNumbersA(numberA);
            Double callDurationTotalForThisNumber = callDataPrint(bw, callsListByNumberA);
            strIndex++;
            String strForPrint = strIndex + " "+ numberA.substring(1,numberA.length()) + " " + round(callDurationTotalForThisNumber, 2) + " секунд";
            bw.println(strForPrint);
            totalCallDuration += callDurationTotalForThisNumber;
        }
        String firstString = " Общая длительность переговоров- " + round(totalCallDuration, 2) + " секунд";
        bw.println(firstString);
        bw.close();
    }

    public Double callDataPrint(PrintStream bw, List<Call> callListByNumberA) {
        Double callDurationTotalForThisNumber = 0.0;
        for (Call call : callListByNumberA) {
            callDurationTotalForThisNumber += call.getDuration();
        }
        return callDurationTotalForThisNumber;
    }
}
