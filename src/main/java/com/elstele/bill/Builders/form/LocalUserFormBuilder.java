package com.elstele.bill.Builders.form;

import com.elstele.bill.form.LocalUserForm;

import java.util.ArrayList;

public class LocalUserFormBuilder {
    private LocalUserForm form;

    public LocalUserFormBuilder build() {
        form = new LocalUserForm();
        return this;
    }

    public LocalUserFormBuilder withId(Integer id) {
        form.setId(id);
        return this;
    }

    public LocalUserFormBuilder withUserName(String username) {
        form.setUsername(username);
        return this;
    }

    public LocalUserFormBuilder withPassword(String password) {
        form.setPassword(password);
        return this;
    }

    public LocalUserFormBuilder withPasswordConfirm(String passwordConfirm) {
        form.setPasswordConfirm(passwordConfirm);
        return this;
    }

    public LocalUserFormBuilder withRoleId(ArrayList<Integer> roleId) {
        form.setRoleId(roleId);
        return this;
    }

    public LocalUserForm getRes() {
        if (form == null) {
            build();
        }
        return form;
    }
}
