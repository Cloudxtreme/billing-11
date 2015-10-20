package com.elstele.bill.form;

import java.util.Date;

public class CallForCSVForm {
    private String numberA;
    private String numberB;
    private String duration;
    private Date startTime;
    private String dirPrefix;
    private String dirDescrpOrg;
    private String costCallTotal;
    private String provider;

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getDirPrefix() {
        return dirPrefix;
    }

    public void setDirPrefix(String dirPrefix) {
        this.dirPrefix = dirPrefix;
    }

    public String getDirDescrpOrg() {
        return dirDescrpOrg;
    }

    public void setDirDescrpOrg(String dirDescrpOrg) {
        this.dirDescrpOrg = dirDescrpOrg;
    }

    public String getCostCallTotal() {
        return costCallTotal;
    }

    public void setCostCallTotal(String costCallTotal) {
        this.costCallTotal = costCallTotal;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }
}
