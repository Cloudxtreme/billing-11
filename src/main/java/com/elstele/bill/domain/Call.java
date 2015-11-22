package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="calls")
public class Call extends CommonDomainBean{
    private String numberA;
    private String numberB;
    private Date startTime;
    private Long duration;
    private String aonKat;
    private String dvoCodeA;
    private String dvoCodeB;
    private Integer secRegular;
    private Integer secPref;
    private Float costRegular;
    private Float costPref;
    private Float costTotal;
    private Integer callDirectionId;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getAonKat() {
        return aonKat;
    }

    public void setAonKat(String aonKat) {
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

    public Integer getSecRegular() {
        return secRegular;
    }

    public void setSecRegular(Integer secRegular) {
        this.secRegular = secRegular;
    }

    public Integer getSecPref() {
        return secPref;
    }

    public void setSecPref(Integer secPref) {
        this.secPref = secPref;
    }

    public Float getCostRegular() {
        return costRegular;
    }

    public void setCostRegular(Float costRegular) {
        this.costRegular = costRegular;
    }

    public Float getCostPref() {
        return costPref;
    }

    public void setCostPref(Float costPref) {
        this.costPref = costPref;
    }

    public Float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Float costTotal) {
        this.costTotal = costTotal;
    }

    public Integer getCallDirectionId() {
        return callDirectionId;
    }

    public void setCallDirectionId(Integer callDirectionId) {
        this.callDirectionId = callDirectionId;
    }
}

