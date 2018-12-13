package com.tlg.core.validation.validators;

import com.tlg.core.service.UserService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.annotations.EmailNotExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailExistsValidator implements ConstraintValidator<EmailNotExists, String> {

    @Autowired
    UserService userService;

    @Override
    public void initialize(EmailNotExists constraintAnnotation) {
        // nothing to initialize
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        try {
            return auth != null && auth.getName().equals(email) || userService.findByIdentifier(email) == null;
        } catch (ServiceException e) {
            return false;
        }
    }
}