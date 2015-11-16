package com.elstele.bill.reportCreators.reportStringsWriter;

import java.io.PrintStream;
import java.util.List;

public class ReportStringsWriter {
    private PrintStream ps;

    public ReportStringsWriter(PrintStream ps) {
        this.ps = ps;
    }

    public void write(List<String> stringList){
        for (String string : stringList){
            ps.println(string);
        }
    }
}
