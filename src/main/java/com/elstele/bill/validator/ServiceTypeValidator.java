package com.elstele.bill.validator;

import com.elstele.bill.datasrv.interfaces.ServiceTypeDataService;
import com.elstele.bill.domain.ServiceType;
import com.elstele.bill.form.ServiceTypeForm;
import com.elstele.bill.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import org.springframework.stereotype.Service;

@Service
public class ServiceTypeValidator implements Validator{
    @Autowired
    ServiceTypeDataService serviceTypeDataService;
    final static Logger LOGGER = LogManager.getLogger(ServiceTypeValidator.class);

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
        if(!serviceTypeDataService.checkUniqueTypeName(service)){
            errors.rejectValue("name", Constants.SERVICE_ERROR_UNIQUE_NAME);
            LOGGER.info(service.getName() + " was trying add non-unique name in ServiceType");
        }
    }
}
