package com.elstele.bill.dao;


import com.elstele.bill.dao.common.CommonDAO;
import com.elstele.bill.domain.IpSubnet;

import java.util.List;

public interface IpSubnetDAO extends CommonDAO<IpSubnet> {
    public List<IpSubnet> getSubnets();
}
