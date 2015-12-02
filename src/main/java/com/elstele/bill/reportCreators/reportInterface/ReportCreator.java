package com.elstele.bill.reportCreators.reportInterface;

import com.elstele.bill.reportCreators.factory.ReportDetails;

public interface ReportCreator<T>{
    public void create(ReportDetails reportDetails);
   /* public void filePrintingCreate(PrintStream bw, String year, String month);
    public List<String> getUniqueNumbersA(String year, String month);
    public List<T> getCallsFromDBByNumbersA(String numberA, String year, String month);*/

}
