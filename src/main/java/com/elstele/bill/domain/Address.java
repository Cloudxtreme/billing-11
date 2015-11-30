package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import org.hibernate.annotations.Cascade;


import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address extends CommonDomainBean{

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="street_id")
    private Street street;

    private String building;
    private String flat;

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }
}
