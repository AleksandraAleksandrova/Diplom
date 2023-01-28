package org.elsys.diplom.service.dto;

import jakarta.validation.constraints.*;
import org.elsys.diplom.service.validation.EndDateAfterStartDate;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@EndDateAfterStartDate
public class ExpenseDTO {
    private Long id;

    @NotBlank(message = "Please enter a name for the new expense")
    @Length(min = 1, max = 50, message = "Name must be between 1 and 50 characters long")
    private String name;

    @NotNull(message = "Please select a start date.")
    private LocalDate startDate;

    @NotNull(message = "Please select an end date.")
    private LocalDate endDate;

    @NotNull(message = "Please enter a value for the new expense.")
    @DecimalMin(value = "0.00", message = "Amount must be positive.")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Enter a valid amount.")
    private String amount;

    @NotNull
    private Long userId;

    @NotNull
    private Long categoryId;

    public ExpenseDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
}
