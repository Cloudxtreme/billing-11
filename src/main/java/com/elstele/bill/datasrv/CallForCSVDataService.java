package com.elstele.bill.datasrv;

import com.elstele.bill.form.CallForCSVForm;

public interface CallForCSVDataService {
    public void addReportData(CallForCSVForm callForCSVForm);
    public void clearReportTable();

}
