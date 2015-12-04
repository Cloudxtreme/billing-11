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
    private String ikNum;
    private String vkNum;
    private String inputTrunk;
    private String outputTrunk;
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

    public Integer getCallDirectionId() {
        return callDirectionId;
    }

    public void setCallDirectionId(Integer callDirectionId) {
        this.callDirectionId = callDirectionId;
    }

    public Float getCostTotal() {
        return costTotal;
    }

    public void setCostTotal(Float costTotal) {
        this.costTotal = costTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Call call = (Call) o;

        if (numberA != null ? !numberA.equals(call.numberA) : call.numberA != null) return false;
        if (numberB != null ? !numberB.equals(call.numberB) : call.numberB != null) return false;
        if (startTime != null ? !startTime.equals(call.startTime) : call.startTime != null) return false;
        if (duration != null ? !duration.equals(call.duration) : call.duration != null) return false;
        if (aonKat != null ? !aonKat.equals(call.aonKat) : call.aonKat != null) return false;
        if (dvoCodeA != null ? !dvoCodeA.equals(call.dvoCodeA) : call.dvoCodeA != null) return false;
        if (dvoCodeB != null ? !dvoCodeB.equals(call.dvoCodeB) : call.dvoCodeB != null) return false;
        if (ikNum != null ? !ikNum.equals(call.ikNum) : call.ikNum != null) return false;
        if (vkNum != null ? !vkNum.equals(call.vkNum) : call.vkNum != null) return false;
        if (inputTrunk != null ? !inputTrunk.equals(call.inputTrunk) : call.inputTrunk != null) return false;
        if (outputTrunk != null ? !outputTrunk.equals(call.outputTrunk) : call.outputTrunk != null) return false;
        if (secRegular != null ? !secRegular.equals(call.secRegular) : call.secRegular != null) return false;
        if (secPref != null ? !secPref.equals(call.secPref) : call.secPref != null) return false;
        if (costRegular != null ? !costRegular.equals(call.costRegular) : call.costRegular != null) return false;
        if (costPref != null ? !costPref.equals(call.costPref) : call.costPref != null) return false;
        if (costTotal != null ? !costTotal.equals(call.costTotal) : call.costTotal != null) return false;
        return !(callDirectionId != null ? !callDirectionId.equals(call.callDirectionId) : call.callDirectionId != null);

    }

    @Override
    public int hashCode() {
        int result = numberA != null ? numberA.hashCode() : 0;
        result = 31 * result + (numberB != null ? numberB.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (aonKat != null ? aonKat.hashCode() : 0);
        result = 31 * result + (dvoCodeA != null ? dvoCodeA.hashCode() : 0);
        result = 31 * result + (dvoCodeB != null ? dvoCodeB.hashCode() : 0);
        result = 31 * result + (ikNum != null ? ikNum.hashCode() : 0);
        result = 31 * result + (vkNum != null ? vkNum.hashCode() : 0);
        result = 31 * result + (inputTrunk != null ? inputTrunk.hashCode() : 0);
        result = 31 * result + (outputTrunk != null ? outputTrunk.hashCode() : 0);
        result = 31 * result + (secRegular != null ? secRegular.hashCode() : 0);
        result = 31 * result + (secPref != null ? secPref.hashCode() : 0);
        result = 31 * result + (costRegular != null ? costRegular.hashCode() : 0);
        result = 31 * result + (costPref != null ? costPref.hashCode() : 0);
        result = 31 * result + (costTotal != null ? costTotal.hashCode() : 0);
        result = 31 * result + (callDirectionId != null ? callDirectionId.hashCode() : 0);
        return result;
    }
}

