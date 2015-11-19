package com.elstele.bill.reportCreators.reportConstants;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;


public class ReportConstants {

    @Autowired
    static ServletContext ctx;

    public static final String OUTPUT_TRUNK = "05";
    public static final String START_DAY = "01";
    public static final String UKRTEL_PROVIDER = "1";
    public static final String VEGA_PROVIDER = "2";
    public static final String REPORTS_PATH = "D:\\";
}
