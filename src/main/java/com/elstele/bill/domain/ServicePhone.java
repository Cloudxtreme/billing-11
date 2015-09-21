package com.elstele.bill.domain;

import javax.persistence.*;

@Entity
@Table(name="ServicePhone")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
public class ServicePhone extends ServiceT {

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}