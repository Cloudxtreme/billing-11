package com.elstele.bill.form;

import com.elstele.bill.utils.Enums.ObjectOperationType;

import java.util.Date;
import java.util.List;

public class AuditedObjectForm {
    private int id;
    private ObjectOperationType changesType;
    private Date changesDate;
    private String changedBy;
    private int changedObjID;
    private String objClass;
    private List<DifferenceForm> changesList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ObjectOperationType getChangesType() {
        return changesType;
    }

    public void setChangesType(ObjectOperationType changesType) {
        this.changesType = changesType;
    }


    public Date getChangesDate() {
        return changesDate;
    }

    public void setChangesDate(Date changesDate) {
        this.changesDate = changesDate;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
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

    public List<DifferenceForm> getChangesList() {
        return changesList;
    }

    public void setChangesList(List<DifferenceForm> changesList) {
        this.changesList = changesList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuditedObjectForm)) return false;

        AuditedObjectForm that = (AuditedObjectForm) o;

        if (id != that.id) return false;
        if (changesList != that.changesList) return false;
        if (changedObjID != that.changedObjID) return false;
        if (changesType != that.changesType) return false;
        if (changesDate != null ? !changesDate.equals(that.changesDate) : that.changesDate != null) return false;
        if (changedBy != null ? !changedBy.equals(that.changedBy) : that.changedBy != null) return false;
        return !(objClass != null ? !objClass.equals(that.objClass) : that.objClass != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (changesType != null ? changesType.hashCode() : 0);
        result = 31 * result + (changesDate != null ? changesDate.hashCode() : 0);
        result = 31 * result + (changedBy != null ? changedBy.hashCode() : 0);
        result = 31 * result + changedObjID;
        result = 31 * result + (objClass != null ? objClass.hashCode() : 0);
        result = 31 * result + (changesList != null ? changesList.hashCode() : 0);
        return result;
    }
}
