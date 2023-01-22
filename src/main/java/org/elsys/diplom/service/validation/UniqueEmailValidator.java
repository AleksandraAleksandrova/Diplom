package org.elsys.diplom.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.elsys.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    UserService userService;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return userService.getUserByEmail(email) == null;
    }
}
