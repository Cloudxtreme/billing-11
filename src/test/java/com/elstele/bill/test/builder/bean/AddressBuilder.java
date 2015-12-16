package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.Address;
import com.elstele.bill.domain.Street;
import org.apache.commons.lang.RandomStringUtils;

import java.security.SecureRandom;

/**
 * Created by ivan on 15/11/30.
 */
public class AddressBuilder implements TestObjectCreator<AddressBuilder, Address> {
    private Address address;
    private SecureRandom random = new SecureRandom();

    public AddressBuilder build(){
        address = new Address();
        return this;
    }

    public Address createRandomAddress(){
        address = new Address();
        address.setBuilding(RandomStringUtils.randomAlphanumeric(2));
        address.setFlat(RandomStringUtils.randomNumeric(2));
        StreetBuilder sb = new StreetBuilder();
        Street street = sb.build().withRandomName().getRes();
        address.setStreet(street);
        return this.getRes();
    }

    public AddressBuilder withId(Integer id){
        address.setId(id);
        return this;
    }

    public AddressBuilder withBuilding(String building){
        address.setBuilding(building);
        return this;
    }

    public AddressBuilder withFlat(String flat){
        address.setFlat(flat);
        return this;
    }

    public AddressBuilder withStreet(String name){
        StreetBuilder sb = new StreetBuilder();
        Street street = sb.build().withName(name).getRes();
        address.setStreet(street);
        return this;
    }

    public Address getRes(){
        if (address == null){
            this.build();
        }
        return address;
    }




}
