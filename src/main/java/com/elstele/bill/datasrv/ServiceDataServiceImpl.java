package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.ServiceAssembler;
import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.dao.ServiceDAO;
import com.elstele.bill.dao.ServiceTypeDAO;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServicePhone;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceDataServiceImpl implements ServiceDataService {

    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private ServiceTypeDAO serviceTypeDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    @Transactional
    public void deleteService(Integer id) {
        serviceDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public List<Service> listServices(){
        return serviceDAO.listServices();
    }

    @Override
    @Transactional
    public String saveService(ServiceForm form) {
        ServiceAssembler assembler = new ServiceAssembler();
        Service service = new Service();
        ServiceType servType = serviceTypeDAO.getById(form.getServiceType().getId());
        if(servType.getServiceType().equals("internet")) {
            service = assembler.fromFormToInternetBean(form);
        }
        if(servType.getServiceType().equals("phone")) {
            service = assembler.fromFormToPhoneBean(form);
        }
        service.setAccount(accountDAO.getById(form.getAccountId()));
        service.setServiceType(servType);

        String message = "Service was successfully ";
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
    public ServiceForm getServiceFormById(Integer id){
        ServiceAssembler assembler = new ServiceAssembler();
        ServiceForm result = null;
        Service bean = serviceDAO.getById(id);
        if (bean != null){
            if (bean instanceof ServiceInternet) {
                ServiceForm form = assembler.fromInternetBeanToForm((ServiceInternet)bean);
                result = form;
            }
            if (bean instanceof ServicePhone) {
                ServiceForm form = assembler.fromPhoneBeanToForm((ServicePhone)bean);
                result = form;
            }
        }
        return result;
    }

    @Override
    @Transactional
    public Service getServiceBeanById(Integer id){
        return  serviceDAO.getById(id);
    }

}
