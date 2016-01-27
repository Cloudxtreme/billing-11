package com.elstele.bill.form;

import java.util.Date;

public class CallForCSVForm {
    private String numberA;
    private String numberB;
    private Integer duration;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CallForCSVForm that = (CallForCSVForm) o;

        if (numberA != null ? !numberA.equals(that.numberA) : that.numberA != null) return false;
        if (numberB != null ? !numberB.equals(that.numberB) : that.numberB != null) return false;
        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (dirPrefix != null ? !dirPrefix.equals(that.dirPrefix) : that.dirPrefix != null) return false;
        if (dirDescrpOrg != null ? !dirDescrpOrg.equals(that.dirDescrpOrg) : that.dirDescrpOrg != null) return false;
        if (costCallTotal != null ? !costCallTotal.equals(that.costCallTotal) : that.costCallTotal != null)
            return false;
        return !(provider != null ? !provider.equals(that.provider) : that.provider != null);

    }

    @Override
    public int hashCode() {
        int result = numberA != null ? numberA.hashCode() : 0;
        result = 31 * result + (numberB != null ? numberB.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (dirPrefix != null ? dirPrefix.hashCode() : 0);
        result = 31 * result + (dirDescrpOrg != null ? dirDescrpOrg.hashCode() : 0);
        result = 31 * result + (costCallTotal != null ? costCallTotal.hashCode() : 0);
        result = 31 * result + (provider != null ? provider.hashCode() : 0);
        return result;
    }
}
