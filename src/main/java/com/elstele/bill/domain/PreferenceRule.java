package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Table(name = "preference_rules", schema = "public", catalog = "billing")
public class PreferenceRule extends CommonDomainBean{
    private int profileId;
    private int rulePriority;
    private String dayOfMonth;
    private String month;
    private String dayOfWeek;
    private Time starttime;
    private Time finishtime;
    private Float tarif;

    @Basic
    @Column(name = "profile_id")
    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    @Basic
    @Column(name = "rule_priority")
    public int getRulePriority() {
        return rulePriority;
    }

    public void setRulePriority(int rulePriority) {
        this.rulePriority = rulePriority;
    }

    @Basic
    @Column(name = "day_of_month")
    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    @Basic
    @Column(name = "month")
    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Basic
    @Column(name = "day_of_week")
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Basic
    @Column(name = "starttime")
    public Time getStarttime() {
        return starttime;
    }

    public void setStarttime(Time starttime) {
        this.starttime = starttime;
    }

    @Basic
    @Column(name = "finishtime")
    public Time getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Time finishtime) {
        this.finishtime = finishtime;
    }

    @Basic
    @Column(name = "tarif")
    public Float getTarif() {
        return tarif;
    }

    public void setTarif(Float tarif) {
        this.tarif = tarif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferenceRule that = (PreferenceRule) o;

        if (profileId != that.profileId) return false;
        if (rulePriority != that.rulePriority) return false;
        if (dayOfMonth != null ? !dayOfMonth.equals(that.dayOfMonth) : that.dayOfMonth != null) return false;
        if (month != null ? !month.equals(that.month) : that.month != null) return false;
        if (dayOfWeek != null ? !dayOfWeek.equals(that.dayOfWeek) : that.dayOfWeek != null) return false;
        if (starttime != null ? !starttime.equals(that.starttime) : that.starttime != null) return false;
        if (finishtime != null ? !finishtime.equals(that.finishtime) : that.finishtime != null) return false;
        if (tarif != null ? !tarif.equals(that.tarif) : that.tarif != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
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
