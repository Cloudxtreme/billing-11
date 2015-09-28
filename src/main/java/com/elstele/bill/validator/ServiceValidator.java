package com.elstele.bill.validator;

import com.elstele.bill.domain.Service;
import com.elstele.bill.form.ServiceForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//import org.springframework.stereotype.Service;

@Component
public class ServiceValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return ServiceForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ServiceForm serviceForm = (ServiceForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateEnd", "date.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateStart", "date.required");
    }
}
