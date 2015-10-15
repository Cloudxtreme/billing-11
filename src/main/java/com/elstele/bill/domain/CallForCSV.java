package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table(name="callForCSV")
public class CallForCSV extends CommonDomainBean {
    private String numberA;
    private String numberB;
    private String duration;
    private String startTime;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
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
