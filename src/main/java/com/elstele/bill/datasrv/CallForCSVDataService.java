package com.elstele.bill.datasrv;

import com.elstele.bill.form.CallForCSVForm;

import java.util.Date;
import java.util.List;

public interface CallForCSVDataService {
    public void addReportData(CallForCSVForm callForCSVForm);
    public void clearReportTable();
    public List<CallForCSVForm> getUniqueNumberA(Date startTime, Date finishTime);
    public Date getDateInterval();
    public List<CallForCSVForm> getCallForCSVByNumberA(String numberA, Date startTime, Date endTime);

}
