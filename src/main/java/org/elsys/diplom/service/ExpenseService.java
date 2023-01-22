package org.elsys.diplom.service;

import org.elsys.diplom.entity.Category;
import org.elsys.diplom.entity.Expense;
import org.elsys.diplom.repository.ExpenseRepository;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.elsys.diplom.service.dto.FilterExpensesDTO;
import org.elsys.diplom.service.mapper.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;


@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    ExpenseMapper expenseMapper;
    @Autowired
    CategoryService categoryService;

    public void addExpense(ExpenseDTO expenseDto){
        Expense newExpense = expenseMapper.toEntity(expenseDto);
        expenseRepository.save(newExpense);
    }

    public List<Expense> getUsersExpenses(Long userId){
        return expenseRepository.findByUserId(userId);
    }

    public HashMap<String, Double> calculateExpenses(List<Expense> expenses){
        HashMap<String, Double> result = new HashMap<>();
        List<Category> categories = categoryService.getAllCategories();
        for (Expense expense : expenses) {
            for(Category category : categories){
                if(category.getName().equals(expense.getCategory().getName())){
                    if(result.containsKey(category.getName())){
                        result.put(category.getName(), result.get(category.getName()) + expense.getAmount());
                    } else {
                        result.put(category.getName(), expense.getAmount());
                    }
                }
            }
        }
        return result;
    }

    public HashMap<String, Double> getLastWeekExpenses(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusDays(7);
        List<Expense> expenses = expenseRepository.findByUserIdAndStartDateBetween(userId, lastWeek, today);
        return calculateExpenses(expenses);
    }

    public HashMap<String, Double> getLastMonthExpenses(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusDays(30);
        List<Expense> expenses = expenseRepository.findByUserIdAndStartDateBetween(userId, lastMonth, today);
        return calculateExpenses(expenses);
    }

    public List<Expense> customFilterExpenses(FilterExpensesDTO filter) {
        List<Expense> expenses;
        if(filter.getCategoryId() == null) {
            expenses = expenseRepository.findByUserIdAndStartDateBetween(filter.getUserId(), filter.getStartPeriod(), filter.getEndPeriod());
        }
        else{
            expenses = expenseRepository.findByUserIdAndCategoryIdAndStartDateBetween(filter.getUserId(), filter.getCategoryId(), filter.getStartPeriod(), filter.getEndPeriod());
        }
        expenses.sort((o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate())); // switched them to be descending
        return expenses;
    }
}
