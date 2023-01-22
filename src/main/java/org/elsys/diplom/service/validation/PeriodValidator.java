package org.elsys.diplom.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.elsys.diplom.service.dto.FilterExpensesDTO;

import java.time.LocalDate;

public class PeriodValidator implements ConstraintValidator<Period, FilterExpensesDTO> {
    @Override
    public boolean isValid(FilterExpensesDTO value, ConstraintValidatorContext context) {
        LocalDate start = value.getStartPeriod();
        LocalDate end = value.getEndPeriod();
        if(start == null || end == null){
            return false;
        }
        return start.isBefore(end) || start.isEqual(end);
    }
}
