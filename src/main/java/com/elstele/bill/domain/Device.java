package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="Device")
public class Device extends CommonDomainBean{

    private String name;
    private String type;
    private String desc;
    private String community;
    private String ip;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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


    public Device() {
    }

    }


