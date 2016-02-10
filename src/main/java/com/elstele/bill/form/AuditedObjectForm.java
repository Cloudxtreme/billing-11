package com.elstele.bill.form;

import com.elstele.bill.utils.Enums.ObjectOperationType;
import com.elstele.bill.utils.Enums.Status;

import java.util.Date;

public class AuditedObjectForm {
    private int id;
    private Status status;
    private ObjectOperationType changesType;
    private String changedObject;
    private Date changesDate;
    private String changedBy;
    private int changedObjID;
    private String objClass;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ObjectOperationType getChangesType() {
        return changesType;
    }

    public void setChangesType(ObjectOperationType changesType) {
        this.changesType = changesType;
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
}
