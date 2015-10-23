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
    private Float costTotal;
    private String ikNum;
    private String vkNum;
    private String inputTrunk;
    private String outputTrunk;
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

    public Float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Float costTotal) {
        this.costTotal = costTotal;
    }

    public String getIkNum() {
        return ikNum;
    }

    public void setIkNum(String LKNum) {
        this.ikNum = LKNum;
    }

    public String getVkNum() {
        return vkNum;
    }

    public void setVkNum(String VKNum) {
        this.vkNum = VKNum;
    }

    public String getInputTrunk() {
        return inputTrunk;
    }

    public void setInputTrunk(String inputTrunk) {
        this.inputTrunk = inputTrunk;
    }

    public String getOutputTrunk() {
        return outputTrunk;
    }

    public void setOutputTrunk(String outputTrunk) {
        this.outputTrunk = outputTrunk;
    }
}

