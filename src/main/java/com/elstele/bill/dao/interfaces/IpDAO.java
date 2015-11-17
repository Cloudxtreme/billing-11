package com.elstele.bill.dao.interfaces;

import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.utils.IpStatus;

import java.util.List;

public interface IpDAO extends CommonDAO<Ip> {
    public List<Ip> getIpAddressList();
    public void setStatusById(IpStatus ipStatus, Integer id);
    public List<Ip> getIpAddressListBySubnetId(Integer id);

}
