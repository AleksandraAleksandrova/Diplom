package org.elsys.diplom.repository;

import org.elsys.diplom.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategoryName(String name);
    List<Expense> findByUserId(Long id);
    List<Expense> findByUserIdAndCategoryName(Long id, String name);
}
