package com.tlg.core.validation.validators;

import com.tlg.core.entity.User;
import com.tlg.core.service.UserService;
import com.tlg.core.service.exceptions.ServiceException;
import com.tlg.core.validation.annotations.UserAssigned;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserAssignedValidator implements ConstraintValidator<UserAssigned, User> {
    @Autowired
    UserService userService;

    @Override
    public void initialize(UserAssigned constraintAnnotation) {
        // nothing to initialize
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        try {
            if (user.getEmail() != null) {
                return userService.findByIdentifier(user.getEmail()) != null;
            }
            return false;
        } catch (ServiceException e) {
            return false;
        }
    }
}
