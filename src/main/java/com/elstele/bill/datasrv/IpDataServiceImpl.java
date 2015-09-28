package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.IpAssembler;
import com.elstele.bill.dao.IpDAO;
import com.elstele.bill.domain.Ip;
import com.elstele.bill.form.IpForm;
import com.elstele.bill.utils.IpStatus;
import com.elstele.bill.utils.Status;
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

    public void update(IpForm ipForm) {
        IpAssembler assembler = new IpAssembler();
        Ip bean = assembler.fromFormToBean(ipForm);
        ipDAO.update(bean);
    }

    public IpForm getById(Integer id) {
        IpAssembler assembler = new IpAssembler();
        Ip bean = ipDAO.getById(id);
        IpForm result = assembler.fromBeanToForm(bean);
        return result;
    }

    @Override
    @Transactional
    public void setStatus(Integer id, IpStatus ipStatus){
        ipDAO.setStatusById(ipStatus, id);
    }

    @Override
    @Transactional
    public List<IpForm> getBySubnetId(Integer id){
        List<IpForm> result = new ArrayList<IpForm>();
        IpAssembler assembler = new IpAssembler();

        List<Ip> beans = ipDAO.getIpAddressListBySubnetId(id);
        for (Ip curBean : beans) {
            IpForm curForm = assembler.fromBeanToForm(curBean);
            result.add(curForm);
        }
        return result;
    }


}
