package org.elsys.diplom.service;

import org.elsys.diplom.entity.Expense;
import org.elsys.diplom.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;

    public void addExpense(Expense expense){
        expenseRepository.save(expense);
    }
}
