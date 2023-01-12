package org.elsys.diplom.service.mapper;

import org.elsys.diplom.entity.Category;
import org.elsys.diplom.entity.Expense;
import org.elsys.diplom.entity.User;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {User.class, Category.class})
public abstract class ExpenseMapper{
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "category.id", target = "categoryId")
    abstract public ExpenseDTO toDto(Expense expense);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "categoryId", target = "category.id")
    abstract public Expense toEntity(ExpenseDTO expenseDTO);

}