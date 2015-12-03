package com.elstele.bill.domain;

import javax.persistence.*;
import static com.elstele.bill.utils.Constants.SERVICE_PHONE;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ServicePhone that = (ServicePhone) o;

        if (!phoneNumber.equals(that.phoneNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        return result;
    }
}