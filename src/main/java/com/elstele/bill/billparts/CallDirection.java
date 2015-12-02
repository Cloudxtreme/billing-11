package com.elstele.bill.billparts;

import javax.persistence.Entity;
import javax.persistence.Id;

public class CallDirection {
    private static final long serialVersionUID = 1L;

    private int id;
    private String prefix;
    private Float tarif;
    private Float tarif_pref;
    private boolean dollar;
    private Integer pref_profile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Float getTarif() {
        return tarif;
    }

    public void setTarif(Float tarif) {
        this.tarif = tarif;
    }

    public Float getTarif_pref() {
        return tarif_pref;
    }

    public void setTarif_pref(Float tarif_pref) {
        this.tarif_pref = tarif_pref;
    }

    public boolean isDollar() {
        return dollar;
    }

    public void setDollar(boolean dollar) {
        this.dollar = dollar;
    }

    public Integer getPref_profile() {
        return pref_profile;
    }

    public void setPref_profile(Integer pref_profile) {
        this.pref_profile = pref_profile;
    }
}
