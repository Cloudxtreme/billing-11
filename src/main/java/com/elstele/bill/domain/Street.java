package com.elstele.bill.domain;

import com.elstele.bill.domain.common.CommonDomainBean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="Streets")
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

}
