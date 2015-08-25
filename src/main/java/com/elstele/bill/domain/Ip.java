package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Set;

@Entity
    @Table(name="ipAddress")
    public class Ip extends CommonDomainBean{

        public String ipName;
        public String net;

    @OneToOne(mappedBy = "ipAdd")
    private Device device;

    public String getIpName() {
        return ipName;
    }

    public void setIpName(String ipName) {
        this.ipName = ipName;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}

