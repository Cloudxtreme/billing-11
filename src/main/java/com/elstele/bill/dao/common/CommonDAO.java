package com.elstele.bill.dao.common;

import com.elstele.bill.domain.common.CommonDomainBean;
import org.hibernate.Filter;
import org.hibernate.Session;

public interface CommonDAO <T> {

    /** Persist the newInstance object into database */
    public Integer create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    public T getById(Integer id);

    /** Save changes made to a persistent object.  */
    public void update(T transientObject);

    /** Save changes made to a persistent object.  */
    public void merge(T transientObject);

    /** Remove an object from persistent storage in the database */
    public void delete(T persistentObject);

    public void delete(Integer id);

    public void setStatusDelete(CommonDomainBean persistentObject);

    public void setStatusDelete(Integer id);

    /** same as update */
    public void save(T transientObject);

    public Filter setFilter(Session session, String filterName);
}
