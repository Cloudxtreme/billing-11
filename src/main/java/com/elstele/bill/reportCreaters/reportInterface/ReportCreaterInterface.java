package com.elstele.bill.reportCreaters.reportInterface;

import java.io.PrintStream;
import java.util.List;

public interface ReportCreaterInterface <T>{
    public void reportCreateMain(String path, String fileName, String year, String month);
    public void filePrintingCreate(PrintStream bw, String year, String month);
    public List<String> getUniqueNumbersA(String year, String month);
    public List<T> getCallsFromDBByNumbersA(String numberA, String year, String month);

}
