package com.elstele.bill.reportCreators.DBgetter;

import com.elstele.bill.datasrv.CallDataService;
import com.elstele.bill.datasrv.CallForCSVDataService;
import com.elstele.bill.reportCreators.dateparser.DateReportParser;
import com.elstele.bill.reportCreators.reportParent.GeneralReportCreator;
import com.elstele.bill.utils.CallTransformerDir;

import java.util.Date;
import java.util.List;

public class GetDataFromDB {
    private CallDataService callDataService;
    private CallForCSVDataService callForCSVDataService;
    private DateReportParser dateReportParser;

    public GetDataFromDB(CallDataService callDataService, String year, String month) {
        this.callDataService = callDataService;
        this.dateReportParser = new DateReportParser(year, month);
    }

    public GetDataFromDB(CallForCSVDataService callForCSVDataService, String year, String month) {
        this.callForCSVDataService = callForCSVDataService;
        this.dateReportParser = new DateReportParser(year, month);
    }

    public List<String> getUniqueNumbersA(String year, String month) {
        Date endTime = dateReportParser.parseEndTime();
        Date startTime = dateReportParser.parseStartTime();
        return callDataService.getUniqueNumberAFromCalls(startTime, endTime);
    }

    public List<CallTransformerDir> getCallsFromDBByNumbersA(String numberA, String year, String month) {
        Date endTime = dateReportParser.parseEndTime();
        Date startTime = dateReportParser.parseStartTime();
        return callDataService.getCallByNumberA(numberA, startTime, endTime);
    }
}
