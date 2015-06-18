package com.elstele.bill.dao;

import com.elstele.bill.domain.LocalUser;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LocalUserDAOImpl implements LocalUserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public LocalUser getLocalUserById(Integer id) {
        LocalUser user = (LocalUser)this.sessionFactory.getCurrentSession().get(LocalUser.class, id);
        return user;
    }

    @Override
    public LocalUser getLocalUserByNameAndPass(String name, String pass) {
        String hql = "from LocalUser where username =:name and password =:pass";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql)
                .setParameter("name", name)
                .setParameter("pass", pass);
        if (!query.list().isEmpty()){
            return (LocalUser)query.list().get(0);
        }
        return null;
    }

    @Override
    public LocalUser save(LocalUser user) {
        return null;
    }

    @Override
    public void update(LocalUser user) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(user);
        this.sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(Integer id) {
        LocalUser user = this.getLocalUserById(id);
        if (user != null) {
            this.sessionFactory.getCurrentSession().delete(user);
        }
    }
}
