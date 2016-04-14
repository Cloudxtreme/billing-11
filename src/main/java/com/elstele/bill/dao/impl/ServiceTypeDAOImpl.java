package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.AuditedObjectDAO;
import com.elstele.bill.dao.interfaces.ServiceTypeDAO;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ServiceTypeDAOImpl extends CommonDAOImpl<ServiceType> implements ServiceTypeDAO {
    @Autowired
    AuditedObjectDAO auditedObjectDAO;

    @Override
    public List listServiceType(){
        String hql = "from ServiceType service where service.status <> 'DELETED' or service.status is null ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }

    @Override
    public List listServiceType(String serviceType, Constants.AccountType bussType){
        String hql = "from ServiceType service where serviceType = :serviceType AND (service.status <> 'DELETED' or service.status is null) AND bussType= :bussType";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("serviceType", serviceType).setParameter("bussType", bussType);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return Collections.emptyList();
    }

    public List<ServiceType> listServiceTypeByBussType(Constants.AccountType bussTyp) {
        String hql = "from ServiceType service where bussType = :busstype AND " +
                "(service.status <> 'DELETED' or service.status is null)";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        query.setParameter("busstype", bussTyp);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }

    @Override
    public ServiceType getByName(String name) {
        Query query = getSessionFactory().getCurrentSession().createQuery("from ServiceType service where service.name =:name ");
        query.setParameter("name", name);
        return (ServiceType)query.uniqueResult();
    }

    @Override
    public Integer create(ServiceType serviceType, String changerName) {
        int id = (int) getSessionFactory().getCurrentSession().save(serviceType);
        auditedObjectDAO.create(serviceType, ObjectOperationType.CREATE, changerName);
        return id;
    }

    @Override
    public void update(ServiceType serviceType, String changerName) {
        getSessionFactory().getCurrentSession().saveOrUpdate(serviceType);
        getSessionFactory().getCurrentSession().flush();
        auditedObjectDAO.create(serviceType, ObjectOperationType.UPDATE, changerName);
    }

}
