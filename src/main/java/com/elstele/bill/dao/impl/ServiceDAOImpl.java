package com.elstele.bill.dao.impl;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.dao.interfaces.ServiceDAO;
import com.elstele.bill.domain.Service;
import org.hibernate.Query;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceDAOImpl extends CommonDAOImpl<Service> implements ServiceDAO {

    @Override
    public List listServices(){
        String hql = "from Service service where service.status <> 'DELETED' or service.status is null ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }
}
