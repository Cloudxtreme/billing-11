package com.elstele.bill.validator;

import com.elstele.bill.datasrv.interfaces.UserRoleDataService;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.UserRoleForm;
import com.elstele.bill.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class UserRoleValidator implements Validator{
    @Autowired
    UserRoleDataService userRoleDataService;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserRole.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRoleForm role = (UserRoleForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.required");
        if (role.getActivityId() == null || role.getActivityId() .size() < 1) {
            errors.rejectValue("activityId", "activity.required");
        }
        if(!userRoleDataService.checkUniqueRoleName(role)){
            errors.rejectValue("name", Constants.USERROLE_ERROR_UNIQUE_NAME);
        }

    }
}
