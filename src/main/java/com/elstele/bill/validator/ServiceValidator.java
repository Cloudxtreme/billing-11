package com.elstele.bill.validator;

import com.elstele.bill.domain.ServiceT;
import com.elstele.bill.form.ServiceForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class ServiceValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return ServiceT.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ServiceForm service = (ServiceForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "price.required");
    }
}
