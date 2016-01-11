package com.elstele.bill.datasrv.impl;

import com.elstele.bill.dao.interfaces.UserStatisticDAO;
import com.elstele.bill.datasrv.interfaces.UserStatisticDataService;
import com.elstele.bill.domain.Radacct;
import com.elstele.bill.utils.CalendarUtils;
import com.elstele.bill.utils.CustomizeCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UserStatisticDataServiceImpl implements UserStatisticDataService {
    @Autowired
    private UserStatisticDAO userStatisticDAO;

    @Override
    @Transactional
    public List<CustomizeCalendar> getCustomizeCalendar(String login, String startDate, String endDate)  throws ParseException {
        CalendarUtils calendarUtils = new CalendarUtils();
        List<CustomizeCalendar> calendarList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date dateStart = sdf.parse(startDate);
        Date dateEnd = sdf.parse(endDate);
        List<Date> listMonthYearBetweenDates = calendarUtils.getMonthYearListBetweenTwoDates(dateStart, dateEnd);

        for (Date curMonthYear : listMonthYearBetweenDates) {
            CustomizeCalendar calendar = new CustomizeCalendar();
            Calendar cal = Calendar.getInstance();
            cal.setTime(curMonthYear);
            int month = cal.get(Calendar.MONTH);
            int year = cal.get(Calendar.YEAR);

            calendar.setMonthName(month);
            calendar.setMonthNumber(month + 1);
            calendar.setYear(year);
            List<CustomizeCalendar.CustomizeDay> statisticDayslist = new ArrayList<>();
            int lastDayOfMonth = calendar.getLastDayOfMonth(year, month);
            List visitDays = userStatisticDAO.getUserActivityStatisticPerDay(login, year + "-" + (month + 1) + "-01", year + "-" + (month + 1) + "-" + lastDayOfMonth);
            for (int i=1; i<=lastDayOfMonth; i++){
                CustomizeCalendar.CustomizeDay statisticDays = calendar.new CustomizeDay();
                statisticDays.setDayNumber(i);
                String addNull = (i<10) ? "0" : "";
                if(!visitDays.contains(""+addNull+i+""))
                    statisticDays.setDisabled("disabled");
                statisticDayslist.add(statisticDays);
            }
            calendar.setDayList(statisticDayslist);
            calendarList.add(calendar);
        }
        return calendarList;
    }

    @Override
    @Transactional
    public List<Radacct> getDailyStatistic(String login, String date) {
        List<Radacct> visitDays = userStatisticDAO.getDailyStatistic(login, date);
        return visitDays;
    }
}
