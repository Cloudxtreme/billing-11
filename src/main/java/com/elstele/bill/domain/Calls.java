package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="calls")
public class Calls extends CommonDomainBean{
    private String numberA;
    private String numberB;
    private String startTime;
    private String duration;
    private Integer aonKat;
    private String dvoCodeA;
    private String dvoCodeB;

    public String getNumberA() {
        return numberA;
    }

    public void setNumberA(String numberA) {
        this.numberA = numberA;
    }

    public String getNumberB() {
        return numberB;
    }

    public void setNumberB(String numberB) {
        this.numberB = numberB;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Integer getAonKat() {
        return aonKat;
    }

    public void setAonKat(Integer aonKat) {
        this.aonKat = aonKat;
    }

    public String getDvoCodeA() {
        return dvoCodeA;
    }

    public void setDvoCodeA(String dvoCodeA) {
        this.dvoCodeA = dvoCodeA;
    }

    public String getDvoCodeB() {
        return dvoCodeB;
    }

    public void setDvoCodeB(String dvoCodeB) {
        this.dvoCodeB = dvoCodeB;
    }

}

