package com.elstele.bill.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallsRequestParamTO {
    private String callNumberA;
    private String callNumberB;
    private int pageResults;
    private Date startDate;
    private Date endDate;

    DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    public String getCallNumberA() {
        return callNumberA;
    }

    public void setCallNumberA(String callNumberA) {
        this.callNumberA = callNumberA;
    }

    public String getCallNumberB() {
        return callNumberB;
    }

    public void setCallNumberB(String callNumberB) {
        this.callNumberB = callNumberB;
    }

    public int getPageResults() {
        return pageResults;
    }

    public void setPageResults(int pageResults) {
        this.pageResults = pageResults;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(String timeRange) throws ParseException {
        if (timeRange.length() >= 16 && timeRange.length() < 36){
            String startDateTemp = timeRange.substring(0, 16);
            startDate = df.parse(startDateTemp);
        }
    }

    public Date getEndDate(){
        return endDate;
    }

    public void setEndDate(String timeRange) throws ParseException {
        if (timeRange.length() >= 16 && timeRange.length() < 36){
            String endDateTemp = timeRange.substring(19, 35);
            endDate = df.parse(endDateTemp);
        }
    }
}
