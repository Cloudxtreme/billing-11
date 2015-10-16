package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.CallForCSV;

import java.util.List;

public interface CallForCSVDAO extends CommonDAO<CallForCSV> {
    public void clearReportDataTable();
    public List<CallForCSV> getUniqueNumberA(String startTime, String finishtime);
    public String getDateInterval ();

}
