package com.elstele.bill.domain;

import javax.persistence.*;
import static com.elstele.bill.utils.Constants.SERVICE_INTERNET;

@Entity
@Table(name="Service")
@DiscriminatorValue(SERVICE_INTERNET)
public class ServiceInternet extends Service {

    private String username;
    private String macaddress;
    private String password;
    private Boolean softblock;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="device")
    private Device device;
    private Integer port;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ip")
    private Ip ipAddress;

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

    public void setIpAddress(Ip ip) {
        this.ipAddress = ip;
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