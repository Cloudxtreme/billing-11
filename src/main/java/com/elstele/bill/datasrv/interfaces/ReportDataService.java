package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.utils.Enums.ResponseToAjax;
import com.elstele.bill.exceptions.IncorrectReportNameException;

import javax.servlet.http.HttpSession;

public interface ReportDataService {
    public ResponseToAjax createReport(String[] reportParametersArray, HttpSession session) throws IncorrectReportNameException;
}
