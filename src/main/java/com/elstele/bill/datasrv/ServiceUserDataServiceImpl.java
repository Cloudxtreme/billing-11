package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.ServiceUserAssembler;
import com.elstele.bill.dao.LocalUserDAO;
import com.elstele.bill.dao.ServiceDAO;
import com.elstele.bill.dao.ServiceUserDAO;
import com.elstele.bill.domain.UserService;
import com.elstele.bill.form.ServiceUserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ServiceUserDataServiceImpl implements ServiceUserDataService {

    @Autowired
    private ServiceUserDAO serviceUserDAO;

    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private LocalUserDAO localUserDAO;

/*
    @Override
    @Transactional
    public void deleteService(Integer id) {
        serviceDAO.setStatusDelete(id);
    }
*/
    @Override
    @Transactional
    public List<UserService> listUserServices(){
        return serviceUserDAO.listUserServices();
    }

    @Override
    @Transactional
    public String saveServiceUser(ServiceUserForm form) {
//        ServiceUserAssembler assembler = new ServiceUserAssembler();
//        UserService service = assembler.fromFormToBean(form);
        UserService service = new UserService();
        service.setUser(localUserDAO.getById(form.getUserId()));
        service.setDateStart(new Date());
        service.setDateEnd(new Date());
        service.setService(serviceDAO.getById(form.getServiceId()));
        service.setId(form.getId());
        String message = "Service was successfully ";
        if(form.isNew()){
            serviceUserDAO.create(service);
            message += "added.";
        }
        else{
            serviceUserDAO.update(service);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public ServiceUserForm getServiceUserFormById(Integer id){
        ServiceUserAssembler assembler = new ServiceUserAssembler();
        ServiceUserForm result = null;
        UserService bean = serviceUserDAO.getById(id);
        if (bean != null){
            ServiceUserForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }
}
