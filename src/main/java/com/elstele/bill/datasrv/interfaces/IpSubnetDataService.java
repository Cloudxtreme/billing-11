package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.IpSubnetForm;

import java.util.HashMap;
import java.util.List;


public interface IpSubnetDataService {
    public List<IpSubnetForm> getIpSubnets();
    public HashMap<Integer, String> getIpSubnetsAsMap();
}
