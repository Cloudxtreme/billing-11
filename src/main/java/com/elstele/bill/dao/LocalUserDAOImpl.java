package com.elstele.bill.dao;

import com.elstele.bill.dao.common.CommonDAOImpl;
import com.elstele.bill.domain.LocalUser;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

@Service
public class LocalUserDAOImpl extends CommonDAOImpl<LocalUser> implements LocalUserDAO {

    @Override
    public LocalUser getLocalUserByNameAndPass(String name, String pass) {
        String hql = "from LocalUser where username =:name and password =:pass";
        Query query = getSessionFactory().getCurrentSession().createQuery(hql)
                .setParameter("name", name)
                .setParameter("pass", pass);
        if (!query.list().isEmpty()){
            return (LocalUser)query.list().get(0);
        }
        return null;
    }

}
