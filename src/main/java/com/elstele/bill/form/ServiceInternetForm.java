package com.elstele.bill.form;

public class ServiceInternetForm {
    private String username;
    private String password;
    private String macaddress;
    private DeviceForm device;
    private IpForm ip;
    private Integer port;

    public ServiceInternetForm(){
    }

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

    public IpForm getIp() {
        return ip;
    }

    public void setIp(IpForm ip) {
        this.ip = ip;
    }

    public DeviceForm getDevice() {
        return device;
    }

    public void setDevice(DeviceForm device) {
        this.device = device;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}