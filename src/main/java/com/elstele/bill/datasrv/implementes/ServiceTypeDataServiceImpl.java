package com.elstele.bill.datasrv.implementes;

import com.elstele.bill.assembler.ServiceTypeAssembler;
import com.elstele.bill.dao.interfaces.ServiceTypeDAO;
import com.elstele.bill.datasrv.interfaces.ServiceTypeDataService;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceTypeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceTypeDataServiceImpl implements ServiceTypeDataService {

    @Autowired
    private ServiceTypeDAO serviceTypeDAO;

    @Override
    @Transactional
    public String saveServiceType(ServiceTypeForm form) {
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        String message = "Service was successfully ";
        ServiceType service = assembler.fromFormToBean(form);
        if(form.isNew()){
            serviceTypeDAO.create(service);
            message += "added.";
        }
        else{
            serviceTypeDAO.update(service);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public List<ServiceType> listServiceType(){
        return serviceTypeDAO.listServiceType();
    }


    @Override
    @Transactional
    public void deleteServiceType(Integer id) {
        serviceTypeDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public ServiceTypeForm getServiceTypeFormById(Integer id){
        ServiceTypeAssembler assembler = new ServiceTypeAssembler();
        ServiceTypeForm result = null;
        ServiceType bean = serviceTypeDAO.getById(id);
        if (bean != null){
            ServiceTypeForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }
}
