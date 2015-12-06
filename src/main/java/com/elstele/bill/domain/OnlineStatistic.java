package com.elstele.bill.domain;

import java.math.BigInteger;

/**
 * Created by ivan on 15/12/05.
 */
public class OnlineStatistic {

    private String username;
    private String user_fio;
    private String nasipaddress;
    private String nasportid;
    private String acctstarttime;
    private BigInteger acctsessiontime;
    private String framedipaddress;
    private BigInteger acctinputoctets;
    private BigInteger acctoutputoctets;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_fio() {
        return user_fio;
    }

    public void setUser_fio(String user_fio) {
        this.user_fio = user_fio;
    }

    public String getNasipaddress() {
        return nasipaddress;
    }

    public void setNasipaddress(String nasipaddress) {
        this.nasipaddress = nasipaddress;
    }

    public String getNasportid() {
        return nasportid;
    }

    public void setNasportid(String nasportid) {
        this.nasportid = nasportid;
    }

    public String getAcctstarttime() {
        return acctstarttime;
    }

    public void setAcctstarttime(String acctstarttime) {
        this.acctstarttime = acctstarttime;
    }

    public BigInteger getAcctsessiontime() {
        return acctsessiontime;
    }

    public void setAcctsessiontime(BigInteger acctsessiontime) {
        this.acctsessiontime = acctsessiontime;
    }

    public String getFramedipaddress() {
        return framedipaddress;
    }

    public void setFramedipaddress(String framedipaddress) {
        this.framedipaddress = framedipaddress;
    }

    public BigInteger getAcctinputoctets() {
        return acctinputoctets;
    }

    public void setAcctinputoctets(BigInteger acctinputoctets) {
        this.acctinputoctets = acctinputoctets;
    }

    public BigInteger getAcctoutputoctets() {
        return acctoutputoctets;
    }

    public void setAcctoutputoctets(BigInteger acctoutputoctets) {
        this.acctoutputoctets = acctoutputoctets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OnlineStatistic that = (OnlineStatistic) o;

        if (!acctinputoctets.equals(that.acctinputoctets)) return false;
        if (!acctoutputoctets.equals(that.acctoutputoctets)) return false;
        if (!acctsessiontime.equals(that.acctsessiontime)) return false;
        if (!acctstarttime.equals(that.acctstarttime)) return false;
        if (!framedipaddress.equals(that.framedipaddress)) return false;
        if (!nasipaddress.equals(that.nasipaddress)) return false;
        if (!nasportid.equals(that.nasportid)) return false;
        if (!user_fio.equals(that.user_fio)) return false;
        if (!username.equals(that.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + user_fio.hashCode();
        result = 31 * result + nasipaddress.hashCode();
        result = 31 * result + nasportid.hashCode();
        result = 31 * result + acctstarttime.hashCode();
        result = 31 * result + acctsessiontime.hashCode();
        result = 31 * result + framedipaddress.hashCode();
        result = 31 * result + acctinputoctets.hashCode();
        result = 31 * result + acctoutputoctets.hashCode();
        return result;
    }
}
