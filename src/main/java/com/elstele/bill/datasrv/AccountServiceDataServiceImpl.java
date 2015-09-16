package com.elstele.bill.datasrv;

import com.elstele.bill.assembler.AccountServiceAssembler;
import com.elstele.bill.dao.AccountDAO;
import com.elstele.bill.dao.AccountServiceDAO;
import com.elstele.bill.dao.ServiceDAO;
import com.elstele.bill.domain.AccountService;
import com.elstele.bill.form.AccountServiceForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AccountServiceDataServiceImpl implements AccountServiceDataService {

    @Autowired
    private AccountServiceDAO accountServiceDAO;

    @Autowired
    private ServiceDAO serviceDAO;

    @Autowired
    private AccountDAO accountDAO;

    @Override
    @Transactional
    public void deleteAccountService(Integer id) {
        accountServiceDAO.setStatusDelete(id);
    }

    @Override
    @Transactional
    public List<AccountService> listAccountServices(){
        return accountServiceDAO.listAccountServices();
    }

    @Override
    @Transactional
    public String saveAccountService(AccountServiceForm form) {
//        ServiceUserAssembler assembler = new ServiceUserAssembler();
//        UserService service = assembler.fromFormToBean(form);
        AccountService service = new AccountService();
        service.setAccount(accountDAO.getById(form.getAccountId()));
        service.setDateStart(new Date());
        service.setDateEnd(new Date());
        service.setService(serviceDAO.getById(form.getServiceId()));
        service.setId(form.getId());
        String message = "Service was successfully ";
        if(form.isNew()){
            accountServiceDAO.create(service);
            message += "added.";
        }
        else{
            accountServiceDAO.merge(service);
            message += "updated.";
        }
        return message;
    }

    @Override
    @Transactional
    public AccountServiceForm getAccountServiceFormById(Integer id){
        AccountServiceAssembler assembler = new AccountServiceAssembler();
        AccountServiceForm result = null;
        AccountService bean = accountServiceDAO.getById(id);
        if (bean != null){
            AccountServiceForm form = assembler.fromBeanToForm(bean);
            result = form;
        }
        return result;
    }

    @Override
    @Transactional
    public AccountService getAccountServiceBeanById(Integer id){
        return  accountServiceDAO.getById(id);
    }

}
