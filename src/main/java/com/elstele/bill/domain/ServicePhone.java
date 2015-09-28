package com.elstele.bill.domain;

import javax.persistence.*;

@Entity
//@Table(name="ServicePhone")
//@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
@Table(name="Service")
@DiscriminatorValue("PHONE")
public class ServicePhone extends Service {

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}