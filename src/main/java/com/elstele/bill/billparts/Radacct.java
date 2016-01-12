package com.elstele.bill.billparts;

import java.math.BigInteger;
import java.util.Date;

public class Radacct {
    private static final long serialVersionUID = 1L;
    private BigInteger radacctid;
    private String username;
    private String nasipaddress;
    private String nasportid;
    private Date acctstarttime;
    private Date acctstoptime;
    private BigInteger acctsessiontime;
    private String framedipaddress;
    private BigInteger acctinputoctets;
    private BigInteger acctoutputoctets;
    private String acctterminatecause;

    public BigInteger getRadacctid() {
        return radacctid;
    }

    public void setRadacctid(BigInteger radacctid) {
        this.radacctid = radacctid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getAcctstarttime() {
        return acctstarttime;
    }

    public void setAcctstarttime(Date acctstarttime) {
        this.acctstarttime = acctstarttime;
    }

    public Date getAcctstoptime() {
        return acctstoptime;
    }

    public void setAcctstoptime(Date acctstoptime) {
        this.acctstoptime = acctstoptime;
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

    public String getAcctterminatecause() {
        return acctterminatecause;
    }

    public void setAcctterminatecause(String acctterminatecause) {
        this.acctterminatecause = acctterminatecause;
    }
}
