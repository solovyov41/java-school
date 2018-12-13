package com.tlg.core.validation.validators;

import com.tlg.core.dto.CityDto;
import com.tlg.core.service.VehicleService;
import com.tlg.core.validation.annotations.CityCoordinatesExist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CityCoordinatesExistValidator implements ConstraintValidator<CityCoordinatesExist, CityDto> {

    @Autowired
    VehicleService vehicleService;

    @Override
    public void initialize(CityCoordinatesExist constraintAnnotation) {
        // nothing to initialize
    }

    @Override
    public boolean isValid(CityDto cityDto, ConstraintValidatorContext context) {
        return cityDto.getLatitude() != null && cityDto.getLongitude() != null;
    }
}