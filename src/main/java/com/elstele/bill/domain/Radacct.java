package com.elstele.bill.domain;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="radacct")
public class Radacct {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name="radacctid", columnDefinition = "serial")
    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer radacctid;
    private String acctsessionid;
    private String acctuniqueid;
    private String username;
    private String groupname;
    private String realm;
    private String nasipaddress;
    private String nasportid;
    private String nasporttype;
    private Date acctstarttime;
    private Date acctstoptime;
    private BigInteger acctsessiontime;
    private String acctauthentic;
    private String connectinfo_start;
    private String connectinfo_stop;
    private BigInteger acctinputoctets;
    private BigInteger acctoutputoctets;
    private String calledstationid;
    private String callingstationid;
    private String acctterminatecause;
    private String servicetype;
    private String xascendsessionsvrkey;
    private String framedprotocol;
    private String framedipaddress;
    private Integer acctstartdelay;
    private Integer acctstopdelay;


    public Integer getRadacctid() {
        return radacctid;
    }

    public void setRadacctid(Integer radacctid) {
        this.radacctid = radacctid;
    }

    public String getAcctsessionid() {
        return acctsessionid;
    }

    public void setAcctsessionid(String acctsessionid) {
        this.acctsessionid = acctsessionid;
    }

    public String getAcctuniqueid() {
        return acctuniqueid;
    }

    public void setAcctuniqueid(String acctuniqueid) {
        this.acctuniqueid = acctuniqueid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
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

    public String getNasporttype() {
        return nasporttype;
    }

    public void setNasporttype(String nasporttype) {
        this.nasporttype = nasporttype;
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

    public String getAcctauthentic() {
        return acctauthentic;
    }

    public void setAcctauthentic(String acctauthentic) {
        this.acctauthentic = acctauthentic;
    }

    public String getConnectinfo_start() {
        return connectinfo_start;
    }

    public void setConnectinfo_start(String connectinfo_start) {
        this.connectinfo_start = connectinfo_start;
    }

    public String getConnectinfo_stop() {
        return connectinfo_stop;
    }

    public void setConnectinfo_stop(String connectinfo_stop) {
        this.connectinfo_stop = connectinfo_stop;
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

    public String getCalledstationid() {
        return calledstationid;
    }

    public void setCalledstationid(String calledstationid) {
        this.calledstationid = calledstationid;
    }

    public String getCallingstationid() {
        return callingstationid;
    }

    public void setCallingstationid(String callingstationid) {
        this.callingstationid = callingstationid;
    }

    public String getAcctterminatecause() {
        return acctterminatecause;
    }

    public void setAcctterminatecause(String acctterminatecause) {
        this.acctterminatecause = acctterminatecause;
    }

    public String getServicetype() {
        return servicetype;
    }

    public void setServicetype(String servicetype) {
        this.servicetype = servicetype;
    }

    public String getXascendsessionsvrkey() {
        return xascendsessionsvrkey;
    }

    public void setXascendsessionsvrkey(String xascendsessionsvrkey) {
        this.xascendsessionsvrkey = xascendsessionsvrkey;
    }

    public String getFramedprotocol() {
        return framedprotocol;
    }

    public void setFramedprotocol(String framedprotocol) {
        this.framedprotocol = framedprotocol;
    }

    public String getFramedipaddress() {
        return framedipaddress;
    }

    public void setFramedipaddress(String framedipaddress) {
        this.framedipaddress = framedipaddress;
    }

    public Integer getAcctstartdelay() {
        return acctstartdelay;
    }

    public void setAcctstartdelay(Integer acctstartdelay) {
        this.acctstartdelay = acctstartdelay;
    }

    public Integer getAcctstopdelay() {
        return acctstopdelay;
    }

    public void setAcctstopdelay(Integer acctstopdelay) {
        this.acctstopdelay = acctstopdelay;
    }
}
