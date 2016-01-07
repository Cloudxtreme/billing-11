package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.domain.Radacct;
import com.elstele.bill.utils.CustomizeCalendar;

import java.text.ParseException;
import java.util.List;

public interface UserStatisticDataService {
    public List<CustomizeCalendar> getCustomizeCalendar(Integer login, String startDate, String endDate)  throws ParseException;
    public List<Radacct> getDailyStatistic(Integer login, String date);
}
