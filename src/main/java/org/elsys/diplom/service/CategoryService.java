package org.elsys.diplom.service;

import org.elsys.diplom.entity.Category;
import org.elsys.diplom.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Retrieves all categories from the database
     * Mainly used for adding new expenses and the pie charts
     * @return a List of all categories
     */
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
}
