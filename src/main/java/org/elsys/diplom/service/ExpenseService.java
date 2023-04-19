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

    /**
     * Maps the expense to an entity and saves it to the database
     * @param expenseDto the expense dto to be mapped and added as entity
     */
    public void addExpense(ExpenseDTO expenseDto){
        expenseRepository.save(expenseMapper.toEntity(expenseDto));
    }


    /**
     * Retrieves all expenses for a user and sorts them by date descending
     * @param userId the id of the user
     * @return a List of the sorted user's expenses
     */
    public List<Expense> getUsersExpenses(Long userId){
        List<Expense> expenses = expenseRepository.findByUserId(userId);
        expenses.sort((o1, o2) -> o2.getStartDate().compareTo(o1.getStartDate())); // switched them to be descending
        return expenses;
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

    /**
     * Calculates the total amount of expenses for each category for the last month
     * @param userId the id of the user for which the expenses are calculated
     * @return HashMap with category name as key and total amount as value
     */
    public HashMap<String, Double> getLastMonthExpenses(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);
        List<Expense> expenses = expenseRepository.findByUserIdAndStartDateBetween(userId, lastMonth, today);
        return calculateExpenses(expenses);
    }


    /**
     * Calculates the total amount of expenses for each category for the last year
     * @param userId the id of the user for which the expenses are calculated
     * @return HashMap with category name as key and total amount as value
     */
    public HashMap<String, Double> getLastYearExpenses(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastYear = today.minusYears(1);
        List<Expense> expenses = expenseRepository.findByUserIdAndStartDateBetween(userId, lastYear, today);
        return calculateExpenses(expenses);
    }

    /**
     * Filters the expenses by category or all categories and a period of time
     * sorts them by date descending
     * @param filter the filter object containing the user id, category id, start and end date
     * @see FilterExpensesDTO
     * @return a list with the expenses that match the filter,
     * may be null if no expenses match
     */
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

    /**
     * Retrieves all expenses that are due to in the next 3 days
     * @return a list with the expenses that are due to in the next 3 days
     * may be null if no expenses are due to in the next 3 days
     */
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

    /**
     * Calculates the total amount of expenses for the last month
     * @param userId the id of the user for which the expenses are calculated
     * @return the total amount of expenses for the last month as a Double
     */
    public Double monthTotal(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);
        return calculateTotal(lastMonth, today, userId);
    }

    /**
     * Calculates the total amount of expenses for the last year
     * @param userId the id of the user for which the expenses are calculated
     * @return the total amount of expenses for the last year as a Double
     */
    public Double yearTotal(Long userId){
        LocalDate today = LocalDate.now();
        LocalDate lastYear = today.minusYears(1);
        return calculateTotal(lastYear, today, userId);
    }
}
