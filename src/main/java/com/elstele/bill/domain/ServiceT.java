package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;

@Entity
@Table(name="Service")
@Inheritance(strategy=InheritanceType.JOINED)
public class ServiceT extends CommonDomainBean{

    @Column(unique = true)
    private String name;
    private String description;
    private Float price;
    private String serviceType;

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
}