package org.elsys.diplom.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.elsys.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    UserService userService;
    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return userService.getUserByUsername(username) == null;
    }
}
