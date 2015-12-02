package com.elstele.bill.datasrv.impl;


import com.elstele.bill.assembler.IpAssembler;
import com.elstele.bill.dao.interfaces.IpSubnetDAO;
import com.elstele.bill.datasrv.interfaces.IpSubnetDataService;
import com.elstele.bill.domain.IpSubnet;
import com.elstele.bill.form.IpSubnetForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class IpSubnetDataServiceImpl implements IpSubnetDataService {

    @Autowired
    private IpSubnetDAO ipSubnetDAO;

    @Override
    @Transactional
    public List<IpSubnetForm> getIpSubnets() {
        List<IpSubnetForm> result = new ArrayList<IpSubnetForm>();
        IpAssembler assembler = new IpAssembler();

        List<IpSubnet> beans = ipSubnetDAO.getSubnets();
        for (IpSubnet curBean : beans) {
            IpSubnetForm curForm = assembler.fromBeanToFormIpSubnet(curBean);
            result.add(curForm);
        }
        return result;
    }
}
