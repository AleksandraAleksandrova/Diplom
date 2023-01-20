package org.elsys.diplom.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.elsys.diplom.service.validation.EndDateAfterStartDate;
import org.springframework.format.annotation.DateTimeFormat;

@EndDateAfterStartDate
public class ExpenseDTO {
    private Long id;
    @NotNull
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters long")
    private String name;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDate;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endDate;
    @NotNull
    private Double amount;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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
