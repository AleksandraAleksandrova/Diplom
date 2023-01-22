package org.elsys.diplom.service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PeriodValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface  Period {
    String message() default "End date should be after start date!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
