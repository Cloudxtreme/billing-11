package com.elstele.bill.validator;

import com.elstele.bill.domain.ServiceInternetAttribute;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceInternetAttributeForm;
import com.elstele.bill.form.ServiceTypeForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class ServiceAttributeValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return ServiceInternetAttribute.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ServiceInternetAttributeForm service = (ServiceInternetAttributeForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "attribute", "attribute.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "operation", "operation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "value.required");
    }
}
