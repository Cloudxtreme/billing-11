package com.elstele.bill.datasrv;

import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.Status;

import java.util.List;

public interface IpDataService {
    public List<IpForm> getIpAddressList();
    public void update(IpForm ipForm);
    public IpForm getById(Integer id);
    public void setStatus(Integer id, Status status);
    public List<IpForm> getBySubnetId(Integer id);
    }
