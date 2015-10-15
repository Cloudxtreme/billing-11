package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.CallForCSV;

public interface CallForCSVDAO extends CommonDAO<CallForCSV> {
    public void clearReportDataTable();

}
