package org.elsys.diplom.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.elsys.diplom.service.dto.ExpenseDTO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, ExpenseDTO> {
    @Override
    public boolean isValid(ExpenseDTO value, ConstraintValidatorContext context) {
        LocalDate startDate = LocalDate.parse(value.getStartDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endDate = LocalDate.parse(value.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return endDate.isAfter(startDate) || endDate.isEqual(startDate);
    }
}
