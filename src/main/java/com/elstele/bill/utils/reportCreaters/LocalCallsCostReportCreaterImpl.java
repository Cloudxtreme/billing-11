package com.elstele.bill.utils.reportCreaters;

import com.elstele.bill.domain.Call;
import com.elstele.bill.utils.reportCreaters.reportsInterface.ReportCreaterInterface;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

@Service
public class LocalCallsCostReportCreaterImpl extends LocalCallsDetailReportCreaterImpl implements ReportCreaterInterface {

    @Override
    public void reportCreateMain(String path, String fileName) throws IOException {
        try {
            PrintStream bw = createFileForWriting(path, fileName);
            filePrintingCreate(bw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filePrintingCreate(PrintStream bw) {
        Double totalLocalCallsCost = 0.0;
        List<String> listWithNumberA = getUniqueNumbersA();
        Integer strIndex = 0;
        Double currentNumberTotalCost = 0.0;
        for (String numberA : listWithNumberA) {
            List<Call> callsListByNumberA = getCallsFromDBByNumbersA(numberA);
            Double callDurationTotalForThisNumber = callDataPrint(bw, callsListByNumberA);
            strIndex++;
            if(callDurationTotalForThisNumber > 36000){
                currentNumberTotalCost = (callDurationTotalForThisNumber - 36000)*0.0009;
            }
            String strForPrint = strIndex + " "+ numberA.substring(1,numberA.length()) + " " + round(currentNumberTotalCost, 2) + " UAH";
            bw.println(strForPrint);
            totalLocalCallsCost += currentNumberTotalCost;
        }
        String firstString = " Общая длительность переговоров- " + round(totalLocalCallsCost, 2) + " UAH";
        bw.println(firstString);
        bw.close();
    }

    @Override
    public Double callDataPrint(PrintStream bw, List<Call> callListByNumberA) {
        Double callDurationTotalForThisNumber = 0.0;
        for (Call call : callListByNumberA) {
            callDurationTotalForThisNumber += call.getDuration();
        }
        return callDurationTotalForThisNumber;
    }
}
