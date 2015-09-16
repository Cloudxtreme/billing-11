package com.elstele.bill.dao.common;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Status;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CommonDAOImpl<T> implements CommonDAO <T> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> type;
    /**
    more example see here
    http://www.codeproject.com/Articles/251166/The-Generic-DAO-pattern-in-Java-with-Spring-3-and
    */
     public CommonDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Integer create(T o) {
        return (Integer)this.sessionFactory.getCurrentSession().save(o);
    }

    @Override
    public T getById(Integer id) {
        T persistentObject = (T)this.sessionFactory.getCurrentSession().get(type, id);
        return persistentObject;
    }

    @Override
    public void update(T transientObject) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(transientObject);
        this.sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void merge(T transientObject) {
        this.sessionFactory.getCurrentSession().merge(transientObject);
        this.sessionFactory.getCurrentSession().flush();
    }

    @Override
    public void delete(T persistentObject) {
        this.sessionFactory.getCurrentSession().delete(persistentObject);
    }

    @Override
    public void delete(Integer id) {
        T persistentObject = this.getById(id);
        if (persistentObject != null) {
            this.sessionFactory.getCurrentSession().delete(persistentObject);
        }
    }

    @Override
    public void setStatusDelete(CommonDomainBean persistentObject){
        persistentObject.setStatus(Status.DELETED);
        this.sessionFactory.getCurrentSession().saveOrUpdate(persistentObject);
    }

    @Override
    public void setStatusDelete(Integer id){
        CommonDomainBean persistentObject = (CommonDomainBean)this.getById(id);
        if (persistentObject != null) {
            persistentObject.setStatus(Status.DELETED);
            this.sessionFactory.getCurrentSession().saveOrUpdate(persistentObject);
        }
    }

    @Override
    public Filter setFilter(Session session, String filterName){
        Filter filter = session.enableFilter(filterName);
        if(filterName == "showActive")
            filter.setParameter("exclude", new String("DELETED"));
        return filter;
    }


    //TODO please read Hibernate docs once again and decide if we really need this method
    @Override
    public void save(T transientObject) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(transientObject);
        this.sessionFactory.getCurrentSession().flush();
    }
}
