package com.elstele.bill.form;

import com.elstele.bill.utils.Constants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

import java.util.Date;

public class ServiceInternetAttributeForm {
    private Integer id;
    private String attribute;
    private String operation;
    private String value;
    private Integer serviceTypeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    //Check if this is for New of Update
    public boolean isNew() {
        return (this.id == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceInternetAttributeForm that = (ServiceInternetAttributeForm) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;
        if (operation != null ? !operation.equals(that.operation) : that.operation != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;
        return !(serviceTypeId != null ? !serviceTypeId.equals(that.serviceTypeId) : that.serviceTypeId != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (serviceTypeId != null ? serviceTypeId.hashCode() : 0);
        return result;
    }
}
