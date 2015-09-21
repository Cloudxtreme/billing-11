package com.elstele.bill.domain;

import javax.persistence.*;

@Entity
@Table(name="ServiceInternet")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class ServiceInternet extends ServiceT {

    private String username;
    private String macaddress;
    private String ip;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMacaddress() {
        return macaddress;
    }

    public void setMacaddress(String macaddress) {
        this.macaddress = macaddress;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}