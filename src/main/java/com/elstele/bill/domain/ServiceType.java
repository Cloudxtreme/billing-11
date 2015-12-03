package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import com.elstele.bill.utils.Constants;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="ServiceType")
@Inheritance(strategy=InheritanceType.JOINED)
public class ServiceType extends CommonDomainBean{

    @Column(unique = true)
    private String name;
    private String description;
    private Float price;
    private String serviceType;
    @Enumerated(EnumType.STRING)
    private Constants.AccountType bussType;

    @OneToMany(mappedBy="serviceType")
    @LazyCollection(LazyCollectionOption.TRUE)
    private Set<ServiceInternetAttribute> serviceInternetAttributes = new HashSet<ServiceInternetAttribute>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getServiceType() {
        return serviceType;
    }
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Set<ServiceInternetAttribute> getServiceInternetAttributes() {
        return serviceInternetAttributes;
    }

    public void setServiceInternetAttributes(Set<ServiceInternetAttribute> serviceInternetAttributes) {
        this.serviceInternetAttributes = serviceInternetAttributes;
    }

    public Constants.AccountType getBussType() {
        return bussType;
    }

    public void setBussType(Constants.AccountType bussType) {
        this.bussType = bussType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ServiceType that = (ServiceType) o;

        if (!name.equals(that.name)) return false;
        if (!price.equals(that.price)) return false;
        if (!serviceType.equals(that.serviceType)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + serviceType.hashCode();
        return result;
    }
}