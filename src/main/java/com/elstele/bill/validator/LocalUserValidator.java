package com.elstele.bill.validator;

import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.LocalUserForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class LocalUserValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRole.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LocalUserForm user = (LocalUserForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
        if (user.getPassword() == null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "passwordConfirm.required");
        }
        if (!user.getPassword().equals(user.getPasswordConfirm())) {
            errors.rejectValue("password", "password.required");
            errors.rejectValue("passwordConfirm", "passwordEqual.required");
        }
        if (user.getRoleId() == null || user.getRoleId().size() < 1) {
            errors.rejectValue("roleId", "role.required");
        }
    }
}
