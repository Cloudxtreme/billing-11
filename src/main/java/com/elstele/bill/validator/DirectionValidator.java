package com.elstele.bill.validator;

import com.elstele.bill.domain.Direction;
import com.elstele.bill.form.DirectionForm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class DirectionValidator implements Validator {
    final static Logger LOGGER = LogManager.getLogger(DirectionValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return Direction.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        DirectionForm form = (DirectionForm) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "prefix", "prefix.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "description.required");
    }
}
