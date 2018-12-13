package com.tlg.core.validation.annotations;

import com.tlg.core.validation.validators.CityCoordinatesExistValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CityCoordinatesExistValidator.class)
@Documented
public @interface CityCoordinatesExist {
    String message() default "{validation.city.no.coordinates}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
