package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@Entity
@Table(name = "preference_rules", schema = "public", catalog = "billing")
public class PreferenceRule extends CommonDomainBean {
    @Basic
    @Column(name = "profile_id")
    private int profileId;
    @Basic
    @Column(name = "rule_priority")
    private int rulePriority;
    @Basic
    @Column(name = "day_of_month")
    private String dayOfMonth;
    @Basic
    @Column(name = "month")
    private String month;
    @Basic
    @Column(name = "day_of_week")
    private String dayOfWeek;
    @Basic
    @Column(name = "starttime")
    private Time starttime;
    @Basic
    @Column(name = "finishtime")
    private Time finishtime;
    @Basic
    @Column(name = "tarif")
    private Float tarif;
    private Date validFrom;
    private Date validTo;


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

    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    public Time getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Time finishtime) {
        this.finishtime = finishtime;
    }

    public Float getTarif() {
        return tarif;
    }

    public void setTarif(Float tarif) {
        this.tarif = tarif;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PreferenceRule)) return false;

        PreferenceRule rule = (PreferenceRule) o;

        if (profileId != rule.profileId) return false;
        if (rulePriority != rule.rulePriority) return false;
        if (dayOfMonth != null ? !dayOfMonth.equals(rule.dayOfMonth) : rule.dayOfMonth != null) return false;
        if (month != null ? !month.equals(rule.month) : rule.month != null) return false;
        if (dayOfWeek != null ? !dayOfWeek.equals(rule.dayOfWeek) : rule.dayOfWeek != null) return false;
        if (starttime != null ? !starttime.equals(rule.starttime) : rule.starttime != null) return false;
        if (finishtime != null ? !finishtime.equals(rule.finishtime) : rule.finishtime != null) return false;
        if (tarif != null ? !tarif.equals(rule.tarif) : rule.tarif != null) return false;
        if (validFrom != null ? !validFrom.equals(rule.validFrom) : rule.validFrom != null) return false;
        return !(validTo != null ? !validTo.equals(rule.validTo) : rule.validTo != null);

    }

    @Override
    public int hashCode() {
        int result = profileId;
        result = 31 * result + rulePriority;
        result = 31 * result + (dayOfMonth != null ? dayOfMonth.hashCode() : 0);
        result = 31 * result + (month != null ? month.hashCode() : 0);
        result = 31 * result + (dayOfWeek != null ? dayOfWeek.hashCode() : 0);
        result = 31 * result + (starttime != null ? starttime.hashCode() : 0);
        result = 31 * result + (finishtime != null ? finishtime.hashCode() : 0);
        result = 31 * result + (tarif != null ? tarif.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (validTo != null ? validTo.hashCode() : 0);
        return result;
    }
}
