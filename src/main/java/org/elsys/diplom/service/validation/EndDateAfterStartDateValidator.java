package org.elsys.diplom.service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.elsys.diplom.service.dto.ExpenseDTO;
import java.time.LocalDate;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, ExpenseDTO> {
    @Override
    public boolean isValid(ExpenseDTO value, ConstraintValidatorContext context) {
        LocalDate startDate = value.getStartDate();
        LocalDate endDate = value.getEndDate();
        if(startDate == null || endDate == null){
            return false;
        }
        return startDate.isBefore(endDate) || startDate.isEqual(endDate);
    }
}
