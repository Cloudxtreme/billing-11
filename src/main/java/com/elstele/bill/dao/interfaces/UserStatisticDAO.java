package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.domain.Radacct;
import com.elstele.bill.utils.CustomizeCalendar;

import java.util.List;

public interface UserStatisticDAO extends CommonDAO<Account> {
    public List getUserActivityStatisticPerDay(Integer login, String startDate, String endDate);
    public List<Radacct> getDailyStatistic(Integer login, String date);
}
