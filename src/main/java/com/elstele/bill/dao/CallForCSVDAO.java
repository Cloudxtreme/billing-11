package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.CallForCSV;

import java.util.Date;
import java.util.List;

public interface CallForCSVDAO extends CommonDAO<CallForCSV> {
    public void clearReportDataTable();
    public List<String> getUniqueNumberAWithProvider(Date startTime, Date finishTime, String provider);
    public List<String> getUniqueNumberA(Date startTime, Date finishTime);
    public List<CallForCSV> getCallForCSVByNumberA(String numberA, Date startTime, Date endTime);
    public List<CallForCSV> getCallForCSVByNumberAWithProvider(String numberA, Date startTime, Date endTime, String provider);
    public Date getDateInterval ();

}
