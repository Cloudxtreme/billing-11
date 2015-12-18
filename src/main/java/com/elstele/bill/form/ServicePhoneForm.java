package com.elstele.bill.form;

import com.elstele.bill.domain.ServiceInternet;

public class ServicePhoneForm {

    private String phoneNumber;

    public ServicePhoneForm(){
    }

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

        ServicePhoneForm that = (ServicePhoneForm) o;

        if (!phoneNumber.equals(that.phoneNumber)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (phoneNumber != null ? phoneNumber.hashCode() : 0);
    }
}