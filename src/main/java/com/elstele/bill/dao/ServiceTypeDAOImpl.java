package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.ServiceType;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceTypeDAOImpl extends CommonDAOImpl<ServiceType> implements ServiceTypeDAO {

    @Override
    public List listAccountServices(){
        String hql = "from AccountService service where service.status <> 'DELETED' or service.status is null ";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql);
        if (!query.list().isEmpty()){
            return query.list();
        }
        return null;
    }
}
