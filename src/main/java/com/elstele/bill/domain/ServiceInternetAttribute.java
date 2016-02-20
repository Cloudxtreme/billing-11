package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;

@Entity
@Table(name="pacattributes")
public class ServiceInternetAttribute  extends CommonDomainBean {

    @Column(name = "attribute")
    private String attribute;
    @Column(name = "op")
    private String operation;
    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name="packetid")
    @JsonIgnore
    private ServiceType serviceType;

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

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceInternetAttribute that = (ServiceInternetAttribute) o;

        if (attribute != null ? !attribute.equals(that.attribute) : that.attribute != null) return false;
        if (operation != null ? !operation.equals(that.operation) : that.operation != null) return false;
        if (serviceType != null && that.serviceType != null) return serviceType.equals(that.serviceType);
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = attribute != null ? attribute.hashCode() : 0;
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        return result;
    }
}