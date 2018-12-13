package com.tlg.core.validation.validators;

import com.tlg.core.dto.CityDto;
import com.tlg.core.service.VehicleService;
import com.tlg.core.validation.annotations.CityCoordinatesExist;
import com.tlg.core.validation.annotations.CityNameNotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CityNameNotEmptyValidator implements ConstraintValidator<CityNameNotEmpty, CityDto> {

    @Autowired
    VehicleService vehicleService;

    @Override
    public void initialize(CityNameNotEmpty constraintAnnotation) {
        // nothing to initialize
    }

    @Override
    public boolean isValid(CityDto cityDto, ConstraintValidatorContext context) {
        return cityDto.getName() != null && !cityDto.getName().isEmpty();
    }
}