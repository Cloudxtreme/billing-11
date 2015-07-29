package com.elstele.bill.validator;

import com.elstele.bill.domain.Activity;
import com.elstele.bill.form.ActivityForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class ActivityValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Activity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ActivityForm activity = (ActivityForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.required");
    }
}
