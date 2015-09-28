package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.Service;
import com.elstele.bill.domain.ServiceInternet;
import com.elstele.bill.domain.ServicePhone;
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
