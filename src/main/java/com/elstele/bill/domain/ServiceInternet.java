package com.elstele.bill.domain;

import javax.persistence.*;

@Entity
@Table(name="ServiceInternet")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class ServiceInternet extends ServiceT {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}