package com.elstele.bill.datasrv;

import com.elstele.bill.domain.CallForCSV;
import com.elstele.bill.form.CallForCSVForm;

import java.util.Date;
import java.util.List;

public interface CallForCSVDataService {
    public void addReportData(CallForCSVForm callForCSVForm);
    public void clearReportTable();
    public List<String> getUniqueNumberAWithProvider(Date startTime, Date finishTime, String provider);
    public List<String> getUniqueNumberA(Date startTime, Date finishTime);
    public List<CallForCSV> getCallForCSVByNumberA(String numberA, Date startTime, Date endTime);
    public List<CallForCSV> getCallForCSVByNumberAWithProvider(String numberA, Date startTime, Date endTime, String provider);
    public String getDescriptionFromDirections(String dirPrefix);

}
