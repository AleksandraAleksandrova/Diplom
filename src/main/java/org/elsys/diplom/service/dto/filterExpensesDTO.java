package org.elsys.diplom.service.dto;


import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class filterExpensesDTO {
    @NotNull
    private Long userId;
    @NotNull
    private Long categoryId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startPeriod;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endPeriod;

    public filterExpensesDTO() {
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
