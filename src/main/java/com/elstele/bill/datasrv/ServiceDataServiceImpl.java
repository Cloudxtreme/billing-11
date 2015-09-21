package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.ServiceAssembler;
import com.elstele.bill.dao.ServiceDAO;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServicePhone;
import com.elstele.bill.domain.ServiceT;
import com.elstele.bill.form.ServiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceDataServiceImpl implements ServiceDataService {

    @Autowired
    private ServiceDAO serviceDAO;

    @Override
    @Transactional
    public String saveService(ServiceForm form) {
        ServiceAssembler assembler = new ServiceAssembler();
        String message = "Service was successfully ";
        ServiceT service = new ServiceT();
        switch(form.getServiceType()){
            case "internet": {
                service = assembler.fromFormToInternetBean(form);
                break;
            }
            case "phone": {
                service = assembler.fromFormToPhoneBean(form);
                break;
            }
        }
        if(form.isNew()){
            serviceDAO.create(service);
            message += "added.";
        }
        else{
            serviceDAO.update(service);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public List<ServiceT> listService(){
        return serviceDAO.listService();
    }


    @Override
    @Transactional
    public void deleteService(Integer id) {
        serviceDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public ServiceForm getServiceFormById(Integer id){
        ServiceAssembler assembler = new ServiceAssembler();
        ServiceForm result = null;
        ServiceT bean = serviceDAO.getById(id);
        if (bean != null){
            ServiceForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }
}
