package com.tlg.core.validation.validators;

import com.tlg.core.service.DriverService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.annotations.DriverPersonalNumNotExists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DriverPersonalNumExistsValidator implements ConstraintValidator<DriverPersonalNumNotExists, String> {

    @Autowired
    DriverService driverService;

    @Override
    public void initialize(DriverPersonalNumNotExists constraintAnnotation) {
        // nothing to initialize
    }

    @Override
    public boolean isValid(String personalNum, ConstraintValidatorContext context) {
        try {
            return driverService.findByIdentifier(personalNum) == null;
        } catch (ServiceException e) {
            return false;
        }
    }
}