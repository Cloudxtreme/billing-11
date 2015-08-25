package com.elstele.bill.datasrv;

import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.form.IpSubnetForm;

import java.util.List;


public interface IpSubnetDataService {
    public List<IpSubnetForm> getIpSubnets();
}
