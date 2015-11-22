package com.elstele.bill.domain;

import javax.persistence.*;

@Entity
@Table(name="Service")
@DiscriminatorValue("INTERNET")
public class ServiceInternet extends Service {

    private String username;
    private String password;
    private String macaddress;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ip")
    private Ip ip;

    private Boolean softblock;

    @ManyToOne(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="device")
    private Device device;
    private Integer port;

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

    public Ip getIp() {
        return ip;
    }

    public void setIp(Ip ip) {
        this.ip = ip;
    }

    public Boolean getSoftblock() {
        return softblock;
    }

    public void setSoftblock(Boolean softblock) {
        this.softblock = softblock;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}