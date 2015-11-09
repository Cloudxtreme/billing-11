package com.elstele.bill.reportCreators.creatorsImpl;

import com.elstele.bill.domain.Call;
import com.elstele.bill.reportCreators.factory.ReportDetails;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.reportCreators.reportInterface.ReportCreator;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.List;

@Service
public class LocalCallsCostGeneralReportCreatorImpl extends LocalCallsDetailGeneralReportCreatorImpl implements ReportCreator {

    public void create(ReportDetails reportDetails) {
        PrintStream bw = createFileForWriting(reportDetails);
        filePrintingCreate(bw, reportDetails.getYear(), reportDetails.getMonth());
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
            GeneralReportCreator.log.info("Report generating is Done");
        } catch (Exception e) {
            GeneralReportCreator.log.error(e);
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
