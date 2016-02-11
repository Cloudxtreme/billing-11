package com.elstele.bill.Builders.form;

import com.elstele.bill.form.AccountForm;
import com.elstele.bill.utils.Constants;
import com.elstele.bill.utils.Enums.Status;

public class AccountFormBuilder{
    private AccountForm accountForm;

    public AccountFormBuilder build() {
        accountForm = new AccountForm();
        accountForm.setStatus(Status.ACTIVE);
        return this;
    }

    public AccountForm getRes() {
        if (accountForm == null){
            build();
        }
        return accountForm;
    }

    public AccountFormBuilder withId(Integer id){
        accountForm.setId(id);
        return this;
    }
    public AccountFormBuilder withAccName(String name){
        accountForm.setAccountName(name);
        return this;
    }
    public AccountFormBuilder withAccType(Constants.AccountType type){
        accountForm.setAccountType(type);
        return this;
    }
    public AccountFormBuilder withBalance(Float balance){
        accountForm.setCurrentBalance(balance);
        return this;
    }
    public AccountFormBuilder withFIO(String fio){
        accountForm.setFio(fio);
        return this;
    }

    //TODO: continue with services

}
