package com.elstele.bill.validator;

import com.elstele.bill.domain.Transaction;
import com.elstele.bill.domain.UserRole;
import com.elstele.bill.form.TransactionForm;
import com.elstele.bill.form.UserRoleForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class TransactionValidator implements Validator{
    @Override
    public boolean supports(Class<?> clazz) {
        return Transaction.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "price.required");
    }
}
