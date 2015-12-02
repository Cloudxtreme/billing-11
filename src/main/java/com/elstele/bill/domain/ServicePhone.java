package com.elstele.bill.domain;

import javax.persistence.*;
import static com.elstele.bill.utils.Constants.Constants.SERVICE_PHONE;

@Entity
@Table(name="Service")
@DiscriminatorValue(SERVICE_PHONE)
public class ServicePhone extends Service {

    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}