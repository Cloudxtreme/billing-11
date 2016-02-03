package com.elstele.bill.datasrv.interfaces;

import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.Enums.IpStatus;

import java.util.HashMap;
import java.util.List;

public interface IpDataService {
    public List<IpForm> getIpAddressList();
    public HashMap<Integer, String> getIpAddressAsMap();
    public void update(IpForm ipForm);
    public IpForm getById(Integer id);
    public void setStatus(Integer id, IpStatus ipStatus);
    public List<IpForm> getBySubnetId(Integer id);
    public HashMap<Integer, String> getIpMapBySubnets(String subnetId);
    }
