package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.ServiceAttributeDAO;
import com.elstele.bill.domain.ServiceInternetAttribute;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceAttributeDAOImpl extends CommonDAOImpl<ServiceInternetAttribute> implements ServiceAttributeDAO {
    public List<ServiceInternetAttribute> getServiceInternetAttributesById(Integer serviceId){
        String hql = "from ServiceInternetAttribute attr where (attr.status <> 'DELETED' or attr.status is null) and attr.serviceType.id = :serviceId ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql)
                .setParameter("serviceId", serviceId);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }
}
