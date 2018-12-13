package com.tlg.core.validation.validators;

import com.tlg.core.service.VehicleService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.annotations.LicenceNumNotExists;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LicenceNumExistsValidator implements ConstraintValidator<LicenceNumNotExists, String> {

    @Autowired
    VehicleService vehicleService;

    @Override
    public void initialize(LicenceNumNotExists constraintAnnotation) {
        // nothing to initialize
    }

    @Override
    public boolean isValid(String licenceNumber, ConstraintValidatorContext context) {
        try {
            return vehicleService.findByIdentifier(licenceNumber) == null;
        } catch (ServiceException e) {
            return false;
        }
    }
}