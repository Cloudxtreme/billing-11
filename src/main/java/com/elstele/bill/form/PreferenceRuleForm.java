package com.elstele.bill.form;

import java.sql.Time;

public class PreferenceRuleForm {
    private int id;
    private int profileId;
    private int rulePriority;
    private String dayOfMonth;
    private String month;
    private String dayOfWeek;
    private String starttime;
    private String finishtime;
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
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public Float getTarif() {
        return tarif;
    }

    public void setTarif(Float tarif) {
        this.tarif = tarif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreferenceRuleForm)) return false;

        PreferenceRuleForm that = (PreferenceRuleForm) o;

        if (id != that.id) return false;
        if (profileId != that.profileId) return false;
        if (rulePriority != that.rulePriority) return false;
        if (dayOfMonth != null ? !dayOfMonth.equals(that.dayOfMonth) : that.dayOfMonth != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        if (dayOfWeek != null ? !dayOfWeek.equals(that.dayOfWeek) : that.dayOfWeek != null) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (finishtime != null ? !finishtime.equals(that.finishtime) : that.finishtime != null) return false;
        return !(tarif != null ? !tarif.equals(that.tarif) : that.tarif != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + profileId;
        result = 31 * result + rulePriority;
        result = 31 * result + (dayOfMonth != null ? dayOfMonth.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (dayOfWeek != null ? dayOfWeek.hashCode() : 0);
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (finishtime != null ? finishtime.hashCode() : 0);
        result = 31 * result + (tarif != null ? tarif.hashCode() : 0);
        return result;
    }
}
