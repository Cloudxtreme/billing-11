package com.elstele.bill.reportCreators.reportStringsWriter;

import java.io.PrintStream;
import java.util.List;

public class ReportStringsWriter {

    public static void write(List<String> stringList, PrintStream ps){
        for (String string : stringList){
            ps.println(string);
        }
    }
}
