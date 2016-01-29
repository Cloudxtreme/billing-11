package com.elstele.bill.reportCreators;

import org.apache.logging.log4j.Logger;

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
    private int rows;
    private int page;
    private String selectedTime;
    private int offset;
    final static Logger log = org.apache.logging.log4j.LogManager.getLogger(CallsRequestParamTO.class);

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

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSelectedTime() {
        return selectedTime;
    }

    public void setSelectedTime(String selectedTime) {
        this.selectedTime = selectedTime;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setStartDate(String timeRange) {
        if (timeRange.length() >= 16 && timeRange.length() < 36) {
            String startDateTemp = timeRange.substring(0, 16);
            try {
                startDate = df.parse(startDateTemp);
            } catch (ParseException e) {
                log.error(e + "Method setStartDate");
            }
        }

    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(String timeRange) {
        if (timeRange.length() >= 16 && timeRange.length() < 36) {
            String endDateTemp = timeRange.substring(19, 35);
            try {
                endDate = df.parse(endDateTemp);
            } catch (ParseException e) {
                log.error(e + "Method setEndDate");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallsRequestParamTO)) return false;

        CallsRequestParamTO that = (CallsRequestParamTO) o;

        if (pageResults != that.pageResults) return false;
        if (rows != that.rows) return false;
        if (page != that.page) return false;
        if (offset != that.offset) return false;
        if (callNumberA != null ? !callNumberA.equals(that.callNumberA) : that.callNumberA != null) return false;
        if (callNumberB != null ? !callNumberB.equals(that.callNumberB) : that.callNumberB != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (selectedTime != null ? !selectedTime.equals(that.selectedTime) : that.selectedTime != null) return false;
        return !(df != null ? !df.equals(that.df) : that.df != null);

    }

    @Override
    public int hashCode() {
        int result = callNumberA != null ? callNumberA.hashCode() : 0;
        result = 31 * result + (callNumberB != null ? callNumberB.hashCode() : 0);
        result = 31 * result + pageResults;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + rows;
        result = 31 * result + page;
        result = 31 * result + (selectedTime != null ? selectedTime.hashCode() : 0);
        result = 31 * result + offset;
        result = 31 * result + (df != null ? df.hashCode() : 0);
        return result;
    }
}
