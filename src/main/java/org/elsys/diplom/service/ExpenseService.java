package org.elsys.diplom.service;

import jakarta.transaction.Transactional;
import org.elsys.diplom.entity.Category;
import org.elsys.diplom.entity.Expense;
import org.elsys.diplom.repository.ExpenseRepository;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.elsys.diplom.service.dto.FilterExpensesDTO;
import org.elsys.diplom.service.mapper.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
        expenseRepository.save(expenseMapper.toEntity(expenseDto));
    }

    public List<Expense> getUsersExpenses(Long userId){
        return expenseRepository.findByUserId(userId);
    }

    private HashMap<String, Double> calculateExpenses(List<Expense> expenses){
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
        LocalDate lastMonth = today.minusMonths(1);
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

    public List<Expense> toBeReminded(){
        return expenseRepository.findByEndDate(LocalDate.now().plusDays(3));
    }

    private Double calculateTotal(LocalDate start, LocalDate end, Long userId){
        List<Expense> expenses = expenseRepository.findByUserIdAndStartDateBetween(userId, start, end);
        Double total = 0.0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        if(total == 0.0)
            return null;
        return total;
    }

    public Double weekTotal(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastWeek = today.minusDays(7);
        return calculateTotal(lastWeek, today, userId);
    }

    public Double monthTotal(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);
        return calculateTotal(lastMonth, today, userId);
    }
}
