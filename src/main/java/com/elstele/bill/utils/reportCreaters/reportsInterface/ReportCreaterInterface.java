package com.elstele.bill.utils.reportCreaters.reportsInterface;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public interface ReportCreaterInterface <T>{
    public void reportCreateMain(String path, String fileName) throws IOException;
    public void filePrintingCreate(PrintStream bw);
    public List<String> getUniqueNumbersA();
    public List<T> getCallsFromDBByNumbersA(String numberA);

}
