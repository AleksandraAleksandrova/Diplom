package org.elsys.diplom.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password must contain digit, lowercase letter and uppercase letter!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
