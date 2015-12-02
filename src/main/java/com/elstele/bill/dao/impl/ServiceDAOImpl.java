package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;

import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServiceInternetAttribute;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceDAOImpl extends CommonDAOImpl<Service> implements ServiceDAO {

    @Autowired
    private IpDataService ipDataService;

    @Override
    public List listServices(){
        String hql = "from Service service where service.status <> 'DELETED' or service.status is null ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }

    @Override
    public String saveService(Service service, boolean isNewObject) {
        String message = "Service was successfully ";
        if (isNewObject) {
            create(service);
            message += "added.";
        } else {
            update(service);
            message += "updated.";
        }
        return message;
    }

    @Override
    public void deleteService(Integer serviceId){
        setStatusDelete(serviceId);
        Service service = getById(serviceId);
        if (service instanceof ServiceInternet)
            ipDataService.setStatus(((ServiceInternet) service).getIpAddress().getId(), IpStatus.FREE);
    }

}
