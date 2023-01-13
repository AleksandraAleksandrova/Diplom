package org.elsys.diplom.service;

import org.elsys.diplom.entity.Expense;
import org.elsys.diplom.repository.ExpenseRepository;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.elsys.diplom.service.mapper.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    ExpenseMapper expenseMapper;

    private boolean validExpense(ExpenseDTO expenseDto){
        return true;
        //return expenseDto.getAmount() >= 0 && expenseDto.getCategoryId() != null && expenseDto.getUserId() != null && expenseDto.getName() != null && expenseDto.getStartDate() != null && expenseDto.getEndDate() != null;
    }

    public void addExpense(ExpenseDTO expenseDto){
        if(validExpense(expenseDto)){
            Expense newExpense = expenseMapper.toEntity(expenseDto);
            // for test purposes, later better
            newExpense.setStartDate(LocalDate.now());
            newExpense.setEndDate(LocalDate.now());
            expenseRepository.save(newExpense);
        }
    }
}
