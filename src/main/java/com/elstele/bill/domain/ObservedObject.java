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
        return Objects.equals(objId, that.objId) &&
                Objects.equals(changedObject, that.changedObject) &&
                Objects.equals(changesDate, that.changesDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(objId, changedObject, changesDate);
    }


}
