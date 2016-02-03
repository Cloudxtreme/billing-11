package com.elstele.bill.reportCreators.reportInterface;

import com.elstele.bill.reportCreators.factory.ReportDetails;

public interface ReportCreator<T>{
    public void create(ReportDetails reportDetails);
}
