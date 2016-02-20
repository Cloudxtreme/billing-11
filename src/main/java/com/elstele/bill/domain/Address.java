package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class Address extends CommonDomainBean{

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="street_id")
    private Street street;

    @Column(length = 8)
    private String building;

    @Column(length = 8)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;

        Address address = (Address) o;

        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        if (building != null ? !building.equals(address.building) : address.building != null) return false;
        return !(flat != null ? !flat.equals(address.flat) : address.flat != null);

    }

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + (building != null ? building.hashCode() : 0);
        result = 31 * result + (flat != null ? flat.hashCode() : 0);
        return result;
    }
}
