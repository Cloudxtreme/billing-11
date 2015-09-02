package com.elstele.bill.dao.common;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Status;

public interface CommonDAO <T> {

    /** Persist the newInstance object into database */
    public Integer create(T newInstance);

    /** Retrieve an object that was previously persisted to the database using
     *   the indicated id as primary key
     */
    public T getById(Integer id);

    /** Save changes made to a persistent object.  */
    public void update(T transientObject);

    /** Remove an object from persistent storage in the database */
    public void delete(T persistentObject);

    public void delete(Integer id);

    /** same as update */
    public void save(T transientObject);

    public void setStatus(CommonDomainBean persistentObject, Status status);

    public void setStatus(Integer id, Status status);

    public void updateAndMerge(T transientObject);

}
