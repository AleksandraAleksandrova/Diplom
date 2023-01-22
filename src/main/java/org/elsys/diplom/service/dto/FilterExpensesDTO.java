package org.elsys.diplom.service.dto;

import jakarta.validation.constraints.NotNull;
import org.elsys.diplom.service.validation.Period;

import java.time.LocalDate;

@Period
public class FilterExpensesDTO {
    @NotNull
    private Long userId;
    private Long categoryId;
    @NotNull(message = "Start date is required")
    private LocalDate startPeriod;
    @NotNull(message = "End date is required")
    private LocalDate endPeriod;

    public FilterExpensesDTO() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public LocalDate getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(LocalDate startPeriod) {
        this.startPeriod = startPeriod;
    }

    public LocalDate getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(LocalDate endPeriod) {
        this.endPeriod = endPeriod;
    }
}
