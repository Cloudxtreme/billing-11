package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ObservedObject")
public class ObservedObject extends CommonDomainBean {
    private Integer objId;

    @Enumerated(EnumType.STRING)
    private ObjectOperationType changesType;

    @JsonProperty
    @Column(length = 500)
    private String changedObject;
    
    private Date changesDate;

    private String changedBy;

    public Integer getObjId() {
        return objId;
    }

    public void setObjId(Integer objId) {
        this.objId = objId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObservedObject)) return false;

        ObservedObject that = (ObservedObject) o;

        if (changedBy != null ? !changedBy.equals(that.changedBy) : that.changedBy != null) return false;
        if (changedObject != null ? !changedObject.equals(that.changedObject) : that.changedObject != null)
            return false;
        if (changesDate != null ? !changesDate.equals(that.changesDate) : that.changesDate != null) return false;
        if (changesType != that.changesType) return false;
        if (objId != null ? !objId.equals(that.objId) : that.objId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = objId != null ? objId.hashCode() : 0;
        result = 31 * result + (changesType != null ? changesType.hashCode() : 0);
        result = 31 * result + (changedObject != null ? changedObject.hashCode() : 0);
        result = 31 * result + (changesDate != null ? changesDate.hashCode() : 0);
        result = 31 * result + (changedBy != null ? changedBy.hashCode() : 0);
        return result;
    }
}
