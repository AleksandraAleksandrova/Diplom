package org.elsys.diplom.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Password must contain at least one digit, one lowercase letter, one uppercase letter and no whitespaces!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
