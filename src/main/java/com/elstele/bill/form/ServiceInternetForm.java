package com.elstele.bill.form;

import com.elstele.bill.domain.ServiceInternet;

public class ServiceInternetForm {
    private String username;
    private String password;
    private String macaddress;
    private DeviceForm device;
    private IpForm ip;
    private Integer port;
    private Boolean softblock;

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

    public Boolean getSoftblock() {
        return softblock;
    }

    public void setSoftblock(Boolean softblock) {
        this.softblock = softblock;
    }


    //TODO correct equals and hashCode (use more fields)
    @Override
    public boolean equals(Object o){
        if(o == this) return true;
        if(o == null || getClass() != o.getClass()) return false;

        ServiceInternetForm that = (ServiceInternetForm) o;

        if(!username.equals(that.username)) return false;
        if(!password.equals(that.password)) return false;
        if(ip!=null ? !ip.equals(that.ip) : that.ip != null) return false;

        return true;
    }

    @Override
    public int hashCode(){
        int result = username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (ip != null ? ip.hashCode() : 0);
        return result;
    }
}