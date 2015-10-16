package com.elstele.bill.datasrv;

import com.elstele.bill.form.CallForCSVForm;

import java.util.List;

public interface CallForCSVDataService {
    public void addReportData(CallForCSVForm callForCSVForm);
    public void clearReportTable();
    public List<CallForCSVForm> getUniqueNumberA(String startTime, String finishTime);
    public String getDateInterval();

}
