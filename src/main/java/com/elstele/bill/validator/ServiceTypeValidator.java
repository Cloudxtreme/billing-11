package com.elstele.bill.validator;

import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceTypeForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class ServiceTypeValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return ServiceType.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ServiceTypeForm service = (ServiceTypeForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "price.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serviceType", "serviceType.required");
    }
}
