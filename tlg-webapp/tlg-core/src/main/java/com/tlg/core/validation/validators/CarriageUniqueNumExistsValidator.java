package com.tlg.core.validation.validators;

import com.tlg.core.service.CarriageService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.annotations.CarriageUniqueNumNotExists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CarriageUniqueNumExistsValidator implements ConstraintValidator<CarriageUniqueNumNotExists, String> {

    @Autowired
    CarriageService carriageService;

    @Override
    public void initialize(CarriageUniqueNumNotExists constraintAnnotation) {
        // nothing to initialize
    }

    @Override
    public boolean isValid(String uniqueNumber, ConstraintValidatorContext context) {
        try {
            return carriageService.findByIdentifier(uniqueNumber) == null;
        } catch (ServiceException e) {
            return false;
        }
    }
}