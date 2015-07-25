package com.elstele.bill.validator;

import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class UserRoleValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRole.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRoleForm role = (UserRoleForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.required");
        if (role.getActivityId() == null || role.getActivityId().size() < 1) {
            errors.rejectValue("activityId", "activity.required");
        }
    }
}
