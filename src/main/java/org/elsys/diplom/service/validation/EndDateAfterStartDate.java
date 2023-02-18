package org.elsys.diplom.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = EndDateAfterStartDateValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDateAfterStartDate {
    String message() default "End date should be after start date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
