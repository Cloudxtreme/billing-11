package com.elstele.bill.form;


import com.elstele.bill.utils.Enums.Status;

import java.util.Date;

public class CallForm {
    private Integer id;
    private String numberA;
    private String numberB;
    private Date startTime;
    private Long duration;
    private String aonKat;
    private String dvoCodeA;
    private String dvoCodeB;
    private Status status;
    private Float costTotal;
    private String ikNum;
    private String vkNum;
    private String inputTrunk;
    private String outputTrunk;
    private Float costRegular;
    private Float costPref;
    private Integer callDirectionId;


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

    public Integer getCallDirectionId() {
        return callDirectionId;
    }

    public void setCallDirectionId(Integer callDirectionId) {
        this.callDirectionId = callDirectionId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public void setIkNum(String ikNum) {
        this.ikNum = ikNum;
    }

    public String getVkNum() {
        return vkNum;
    }

    public void setVkNum(String vkNum) {
        this.vkNum = vkNum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallForm callForm = (CallForm) o;

        if (id != null ? !id.equals(callForm.id) : callForm.id != null) return false;
        if (numberA != null ? !numberA.equals(callForm.numberA) : callForm.numberA != null) return false;
        if (numberB != null ? !numberB.equals(callForm.numberB) : callForm.numberB != null) return false;
        if (startTime != null ? !startTime.equals(callForm.startTime) : callForm.startTime != null) return false;
        if (duration != null ? !duration.equals(callForm.duration) : callForm.duration != null) return false;
        if (aonKat != null ? !aonKat.equals(callForm.aonKat) : callForm.aonKat != null) return false;
        if (dvoCodeA != null ? !dvoCodeA.equals(callForm.dvoCodeA) : callForm.dvoCodeA != null) return false;
        if (dvoCodeB != null ? !dvoCodeB.equals(callForm.dvoCodeB) : callForm.dvoCodeB != null) return false;
        if (status != null && callForm.status != null) {
            return status == callForm.status;
        }
        if (costTotal != null ? !costTotal.equals(callForm.costTotal) : callForm.costTotal != null) return false;
        if (ikNum != null ? !ikNum.equals(callForm.ikNum) : callForm.ikNum != null) return false;
        if (vkNum != null ? !vkNum.equals(callForm.vkNum) : callForm.vkNum != null) return false;
        if (inputTrunk != null ? !inputTrunk.equals(callForm.inputTrunk) : callForm.inputTrunk != null) return false;
        if (outputTrunk != null ? !outputTrunk.equals(callForm.outputTrunk) : callForm.outputTrunk != null)
            return false;
        if (costRegular != null ? !costRegular.equals(callForm.costRegular) : callForm.costRegular != null)
            return false;
        if (costPref != null ? !costPref.equals(callForm.costPref) : callForm.costPref != null) return false;
        return !(callDirectionId != null ? !callDirectionId.equals(callForm.callDirectionId) : callForm.callDirectionId != null);

    }

}

