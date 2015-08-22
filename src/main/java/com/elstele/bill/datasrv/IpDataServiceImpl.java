package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.IpAssembler;
import com.elstele.bill.dao.IpDAO;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.form.IpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class IpDataServiceImpl implements IpDataService {

    @Autowired
    IpDAO ipDAO;

    @Override
    @Transactional
    public List<IpForm> getIpAddressList() {
        List<IpForm> result = new ArrayList<IpForm>();
        IpAssembler assembler = new IpAssembler();

        List<Ip> beans = ipDAO.getIpAddressList();
        for (Ip curBean : beans) {
            IpForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }
}
