package com.elstele.bill.utils;

import java.util.List;

public class CustomizeCalendar extends CalendarUtils{
    private String monthName;
    private Integer monthNumber;
    private Integer year;
    private List<CustomizeDay> dayList;

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public void setMonthName(Integer monthNumber) {
        this.monthName = getMonthNameByNumber(monthNumber);
    }

    public Integer getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(Integer monthNumber) {
        this.monthNumber = monthNumber;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<CustomizeDay> getDayList() {
        return dayList;
    }

    public void setDayList(List<CustomizeDay> dayList) {
        this.dayList = dayList;
    }

    public class CustomizeDay {
        private Integer dayNumber;
        private String cssClass;
        private String disabled;

        public Integer getDayNumber() {
            return dayNumber;
        }
        public void setDayNumber(Integer dayNumber) {
            this.dayNumber = dayNumber;
        }
        public String getCssClass() {
            return cssClass;
        }
        public void setCssClass(String cssClass) {
            this.cssClass = cssClass;
        }

        public String getDisabled() {
            return disabled;
        }

        public void setDisabled(String disabled) {
            this.disabled = disabled;
        }
    }
}
