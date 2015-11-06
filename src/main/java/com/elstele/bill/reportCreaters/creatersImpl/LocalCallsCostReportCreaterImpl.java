package com.elstele.bill.reportCreaters.creatersImpl;

import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreaters.reportParent.ReportCreater;
import com.elstele.bill.reportCreaters.reportInterface.ReportCreaterInterface;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.List;

@Service
public class LocalCallsCostReportCreaterImpl extends LocalCallsDetailReportCreaterImpl implements ReportCreaterInterface {

    public void reportCreateMain(String path, String fileName, String year, String month) {
        PrintStream bw = createFileForWriting(path, fileName, year, month);
        filePrintingCreate(bw, year, month);
    }

    @Override
    public void filePrintingCreate(PrintStream bw, String year, String month) {
        try {
            Double totalLocalCallsCost = 0.0;
            List<String> listWithNumberA = getUniqueNumbersA(year, month);
            Integer strIndex = 0;
            Double currentNumberTotalCost = 0.0;
            for (String numberA : listWithNumberA) {
                List<Call> callsListByNumberA = getCallsFromDBByNumbersA(numberA, year, month);
                Double callDurationTotalForThisNumber = callDataPrint(bw, callsListByNumberA);
                strIndex++;
                if (callDurationTotalForThisNumber > 36000) {
                    currentNumberTotalCost = (callDurationTotalForThisNumber - 36000) * 0.0009;
                }
                String strForPrint = strIndex + " " + numberA.substring(1, numberA.length()) + " " + round(currentNumberTotalCost, 2) + " UAH";
                bw.println(strForPrint);
                totalLocalCallsCost += currentNumberTotalCost;
            }
            String firstString = " Общая длительность переговоров- " + round(totalLocalCallsCost, 2) + " UAH";
            bw.println(firstString);
            bw.close();
            ReportCreater.log.info("Report generating is Done");
        } catch (Exception e) {
            ReportCreater.log.error(e);
        }
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
