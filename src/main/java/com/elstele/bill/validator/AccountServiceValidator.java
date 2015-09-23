package com.elstele.bill.validator;

import com.elstele.bill.domain.AccountService;
import com.elstele.bill.form.AccountServiceForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

//import org.springframework.stereotype.Service;

@Component
public class AccountServiceValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return AccountServiceForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountServiceForm serviceForm = (AccountServiceForm) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateEnd", "date.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateStart", "date.required");
    }
}
