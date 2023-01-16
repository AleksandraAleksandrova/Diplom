package org.elsys.diplom.service;

import org.elsys.diplom.entity.Expense;
import org.elsys.diplom.repository.ExpenseRepository;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.elsys.diplom.service.mapper.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
            expenseRepository.save(newExpense);
        }
    }

    public List<Expense> getUsersExpenses(Long userId){
        return expenseRepository.findByUserId(userId);
    }

    public List<Expense> getLastWeekExpenses(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusDays(7);
        List<Expense> expenses = expenseRepository.findByUserIdAndStartDateBetween(userId, lastWeek, today);
        expenses.sort((e1, e2) -> e2.getStartDate().compareTo(e1.getStartDate())); //descending
        return expenses;
    }

}
