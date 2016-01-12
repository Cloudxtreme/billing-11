package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Account;
import com.elstele.bill.billparts.Radacct;

import java.util.List;

public interface UserStatisticDAO extends CommonDAO<Account> {
    public List getUserActivityStatisticPerDay(String login, String startDate, String endDate);
    public List<Radacct> getDailyStatistic(String login, String date);
}
