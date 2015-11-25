package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.utils.exceptions.IncorrectReportNameException;

public interface ReportDataService {
    public ResponseToAjax createReport(String[] reportParametersArray) throws IncorrectReportNameException;
}
