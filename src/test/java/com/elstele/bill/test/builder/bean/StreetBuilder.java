package com.elstele.bill.test.builder.bean;

import com.elstele.bill.domain.Street;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by ivan on 15/11/30.
 */
public class StreetBuilder implements TestObjectCreator<StreetBuilder, Street>{

    private SecureRandom random = new SecureRandom();
    private Street street;
    private Integer id;

    public StreetBuilder build(){
        street = new Street();
        return this;
    }

    public StreetBuilder withName(String name){
        street.setName(name);
        return this;
    }
    public StreetBuilder withId(Integer id){
        street.setId(id);
        return this;
    }

    public StreetBuilder withRandomName(){
        String name = new BigInteger(130, random).toString(32);
        street.setName(name);
        return this;
    }

    public Street getRes(){
        if (street == null){
            this.build();
        }
        return street;
    }

}
