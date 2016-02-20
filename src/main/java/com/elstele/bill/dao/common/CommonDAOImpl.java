package com.elstele.bill.dao.common;

import com.elstele.bill.dao.impl.AuditedObjectDAOImpl;
import com.elstele.bill.domain.common.AuditedDomainBean;
import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.elstele.bill.utils.Enums.Status;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class CommonDAOImpl<T> implements CommonDAO <T> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<T> type;

    /**
     * more example see here
     * http://www.codeproject.com/Articles/251166/The-Generic-DAO-pattern-in-Java-with-Spring-3-and
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


    public Integer create(T o) {
        Integer res = (Integer)this.sessionFactory.getCurrentSession().save(o);
        callAuditDAO(o, ObjectOperationType.CREATE);
        return res;
    }


    public T getById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        setFilter(session,"showActive");
        T persistentObject = (T) session.get(type, id);
        return persistentObject;
    }

    public T getAllById(Integer id) {
        Session session = this.sessionFactory.getCurrentSession();
        T persistentObject = (T) session.get(type, id);
        return persistentObject;
    }


    public void update(T transientObject) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(transientObject);
        this.sessionFactory.getCurrentSession().flush();
        callAuditDAO(transientObject, ObjectOperationType.UPDATE);
    }


    public void merge(T transientObject) {
        this.sessionFactory.getCurrentSession().merge(transientObject);
        callAuditDAO(transientObject, ObjectOperationType.UPDATE);
    }


    public void updateAndMerge(T transientObject) {
        transientObject = (T)sessionFactory.getCurrentSession().merge(transientObject);
        this.sessionFactory.getCurrentSession().saveOrUpdate(transientObject);
        this.sessionFactory.getCurrentSession().flush();
        callAuditDAO(transientObject, ObjectOperationType.UPDATE);
    }


    public void delete(T persistentObject) {
        this.sessionFactory.getCurrentSession().delete(persistentObject);
        callAuditDAO(persistentObject, ObjectOperationType.DELETE);
    }

    public void delete(Integer id) {
        T persistentObject = this.getById(id);
        if (persistentObject != null) {
            this.sessionFactory.getCurrentSession().delete(persistentObject);
            callAuditDAO(persistentObject, ObjectOperationType.DELETE);
        }
    }

    //TODO please read Hibernate docs once again, and decide if we really need this method
    public void save(T transientObject) {
        this.sessionFactory.getCurrentSession().saveOrUpdate(transientObject);
        this.sessionFactory.getCurrentSession().flush();
        callAuditDAO(transientObject, ObjectOperationType.UPDATE);
    }


    public void setStatus(CommonDomainBean persistentObject, Status status) {
        persistentObject.setStatus(status);
        this.sessionFactory.getCurrentSession().saveOrUpdate(persistentObject);
        callAuditDAO(persistentObject, ObjectOperationType.UPDATE);
    }


    public void setStatus(Integer id, Status status) {
        CommonDomainBean persistentObject = (CommonDomainBean) this.getById(id);
        if (persistentObject != null) {
            persistentObject.setStatus(status);
            this.sessionFactory.getCurrentSession().saveOrUpdate(persistentObject);
            callAuditDAO(persistentObject, ObjectOperationType.UPDATE);
        }
    }


    public Filter setFilter(Session session, String filterName){
        Filter filter = session.enableFilter(filterName);
        if(filterName == "showActive")
            filter.setParameter("exclude", new String("DELETED"));
        return filter;
    }

    public void setStatusDelete(CommonDomainBean persistentObject) {
        persistentObject.setStatus(Status.DELETED);
        this.sessionFactory.getCurrentSession().saveOrUpdate(persistentObject);
        callAuditDAO(persistentObject, ObjectOperationType.UPDATE);
    }


    public void setStatusDelete(Integer id) {
        CommonDomainBean persistentObject = (CommonDomainBean) this.getById(id);
        if (persistentObject != null) {
            persistentObject.setStatus(Status.DELETED);
            this.sessionFactory.getCurrentSession().saveOrUpdate(persistentObject);
            callAuditDAO(persistentObject, ObjectOperationType.UPDATE);
        }
    }


    private void callAuditDAO(T o, ObjectOperationType operationType) {
        if (o instanceof AuditedDomainBean){
            //auditDAO.create((CommonDomainBean) o, operationType, "anonymous");
        }
    }

    private void callAuditDAO(CommonDomainBean o, ObjectOperationType operationType) {
        if (o instanceof AuditedDomainBean){
            //auditDAO.create(o, operationType, "anonymous");
        }
    }

}

