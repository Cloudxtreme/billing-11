package com.elstele.bill.domain;

import javax.persistence.*;

@Entity
@Table(name="Service")
@DiscriminatorValue("INTERNET")
public class ServiceInternet extends Service {

    private String username;
    private String macaddress;
    private String password;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ipAddress_id")
    private Ip ipAddress;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="device_id")
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

    public Ip getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(Ip ipAddress) {
        this.ipAddress = ipAddress;
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