package com.elstele.bill.datasrv;

import com.elstele.bill.domain.ServiceT;
import com.elstele.bill.domain.UserService;
import com.elstele.bill.form.ServiceForm;
import com.elstele.bill.form.ServiceUserForm;

import java.util.List;

public interface ServiceUserDataService {

/*
    public String saveService(ServiceForm form);
    public void deleteService(Integer id);
*/
    public String saveServiceUser(ServiceUserForm form);
    public ServiceUserForm getServiceUserFormById(Integer id);
    public List<UserService> listUserServices();

}
