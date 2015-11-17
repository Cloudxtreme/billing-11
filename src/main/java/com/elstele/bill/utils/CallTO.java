package com.elstele.bill.utils;

import java.math.BigInteger;
import java.util.Date;

public class CallTO {
    private String numberb;
    private Date starttime;
    private BigInteger duration;
    private Float costtotal;
    private String description;
    private String prefix;

    public String getNumberb() {
        return numberb;
    }

    public void setNumberb(String numberb) {
        this.numberb = numberb;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public BigInteger getDuration() {
        return duration;
    }

    public void setDuration(BigInteger duration) {
        this.duration = duration;
    }

    public Float getCosttotal() {
        return costtotal;
    }

    public void setCosttotal(Float costtotal) {
        this.costtotal = costtotal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
