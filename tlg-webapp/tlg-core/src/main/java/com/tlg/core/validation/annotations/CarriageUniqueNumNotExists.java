package com.tlg.core.validation.annotations;

import com.tlg.core.validation.validators.CarriageUniqueNumExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CarriageUniqueNumExistsValidator.class)
@Documented
public @interface CarriageUniqueNumNotExists {
    String message() default "{validation.unique.number.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}