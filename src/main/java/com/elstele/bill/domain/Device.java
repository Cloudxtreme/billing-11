package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Device")
public class Device extends CommonDomainBean{

    private Integer id;
    private String devicename;
    private String devtype;
    private String devdesc;
    private String community;
    private String ip;

    public String getDevtype() {
        return devtype;
    }

    public void setDevtype(String devtype) {
        this.devtype = devtype;
    }

    public String getDevdesc() {
        return devdesc;
    }

    public void setDevdesc(String devdesc) {
        this.devdesc = devdesc;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    public Device() {
    }

    }


