package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;
import org.javers.core.metamodel.annotation.ValueObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Streets")
@ValueObject
public class Street extends CommonDomainBean {
    @Column(name = "street_name")
    private String name;

    public Street(){};  //for hibernate we need default constructor

    public Street(Integer id, String name){
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Street)) return false;

        Street street = (Street) o;
        return !(name != null ? !name.equals(street.name) : street.name != null);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
