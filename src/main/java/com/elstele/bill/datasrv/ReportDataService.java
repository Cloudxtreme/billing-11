package com.elstele.bill.datasrv;

import com.elstele.bill.utils.ResponseToAjax;

public interface ReportDataService {
    public ResponseToAjax createReport(String[] reportParametersArray);
}
