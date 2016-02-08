package com.elstele.bill.billparts;

import java.util.Date;

public class CallBillRule {
    private int id;
    private int profileId;
    private int rulePriority;
    private String dayofmonth;
    private String dayofweek;
    private String month;
    private Date starttime;
    private Date finishtime;
    private Float tarif;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public int getRulePriority() {
        return rulePriority;
    }

    public void setRulePriority(int rulePriority) {
        this.rulePriority = rulePriority;
    }

    public String getDayOfMonth() {
        return dayofmonth;
    }

    public void setDayOfMonth(String dayofmonth) {
        this.dayofmonth = dayofmonth;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public void setDayofweek(String dayofweek) {
        this.dayofweek = dayofweek;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public Float getTarif() {
        return tarif;
    }

    public void setTarif(Float tarif) {
        this.tarif = tarif;
    }
}
