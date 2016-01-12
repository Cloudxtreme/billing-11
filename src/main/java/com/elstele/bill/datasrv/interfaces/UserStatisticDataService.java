package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.billparts.Radacct;
import com.elstele.bill.utils.CustomizeCalendar;

import java.text.ParseException;
import java.util.List;

public interface UserStatisticDataService {
    public List<CustomizeCalendar> getCustomizeCalendar(String login, String startDate, String endDate)  throws ParseException;
    public List<Radacct> getDailyStatistic(String login, String date);
}
