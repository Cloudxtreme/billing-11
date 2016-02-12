package com.elstele.bill.test.builder.form;

import com.elstele.bill.form.AddressForm;

public class AddressFormBuilder {
    private AddressForm form;

    public AddressFormBuilder build(){
        form = new AddressForm();
        return this;
    }

    public AddressFormBuilder withId(Integer value){
        form.setId(value);
        return this;
    }

    public AddressFormBuilder withStreet(String value){
        form.setStreet(value);
        return this;
    }

    public AddressFormBuilder withBuilding(String value){
        form.setBuilding(value);
        return this;
    }

    public AddressFormBuilder withFlat(String value){
        form.setFlat(value);
        return this;
    }

    public AddressFormBuilder withStreetId(Integer value){
        form.setStreetId(value);
        return this;
    }

    public AddressForm getRes(){
        if(form == null){
            build();
        }
        return form;
    }
}
