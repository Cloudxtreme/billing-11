package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "AuditedObject")
public class AuditedObject extends CommonDomainBean {

    @Enumerated(EnumType.STRING)
    @Column(length = 7)
    private ObjectOperationType changesType;

    @JsonProperty
    @Column(length = 500)
    private String changedObject;

    private Date changesDate;

    @Column(length = 16)
    private String changedBy;

    private int changedObjID;

    @Column(length = 30)
    private String objClass;

    public String getChangedObject() {
        return changedObject;
    }

    public void setChangedObject(String changedObject) {
        this.changedObject = changedObject;
    }

    public Date getChangesDate() {
        return changesDate;
    }

    public void setChangesDate(Date changesDate) {
        this.changesDate = changesDate;
    }

    public ObjectOperationType getChangesType() {
        return changesType;
    }

    public void setChangesType(ObjectOperationType changesType) {
        this.changesType = changesType;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changer) {
        this.changedBy = changer;
    }

    public int getChangedObjID() {
        return changedObjID;
    }

    public void setChangedObjID(int changedObjID) {
        this.changedObjID = changedObjID;
    }

    public String getObjClass() {
        return objClass;
    }

    public void setObjClass(String objClass) {
        this.objClass = objClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditedObject)) return false;

        AuditedObject that = (AuditedObject) o;

        if (changedBy != null ? !changedBy.equals(that.changedBy) : that.changedBy != null) return false;
        if (changedObject != null ? !changedObject.equals(that.changedObject) : that.changedObject != null)
            return false;
        if (changesDate != null ? !changesDate.equals(that.changesDate) : that.changesDate != null) return false;
        if (changesDate != null ? !changesDate.equals(that.changesDate) : that.changesDate != null) return false;
        if (objClass != null ? !objClass.equals(that.objClass) : that.objClass != null) return false;
        if (changedObjID != that.changedObjID) return false;
        if (changesType != that.changesType) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = changesType != null ? changesType.hashCode() : 0;
        result = 31 * result + (changedObject != null ? changedObject.hashCode() : 0);
        result = 31 * result + (changesDate != null ? changesDate.hashCode() : 0);
        result = 31 * result + (changedBy != null ? changedBy.hashCode() : 0);
        result = 31 * result + (objClass != null ? objClass.hashCode() : 0);
        result = 31 * result + changedObjID;
        return result;
    }
}
