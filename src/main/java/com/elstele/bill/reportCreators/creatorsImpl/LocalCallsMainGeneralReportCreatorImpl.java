package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.datasrv.interfaces.CallDataService;
import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;

import java.io.PrintStream;
import java.util.List;

public class LocalCallsMainGeneralReportCreatorImpl extends LocalCallsDetailGeneralReportCreatorImpl implements ReportCreator {

    public LocalCallsMainGeneralReportCreatorImpl(CallDataService callDataService) {
        super(callDataService);
    }

    public void create(ReportDetails reportDetails) {
        PrintStream bw = createFileForWriting(reportDetails);
        filePrintingCreate(bw, reportDetails.getYear(), reportDetails.getMonth());
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
