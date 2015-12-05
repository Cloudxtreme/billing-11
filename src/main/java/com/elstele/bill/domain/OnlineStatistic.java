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
}
