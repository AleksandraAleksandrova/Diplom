package org.elsys.diplom.data;

import org.elsys.diplom.entity.Category;
import org.elsys.diplom.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryInit implements ApplicationRunner {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Food"));
            categoryRepository.save(new Category("Travel"));
            categoryRepository.save(new Category("Clothing"));
            categoryRepository.save(new Category("Hobby"));
            categoryRepository.save(new Category("Sport"));
            categoryRepository.save(new Category("Education"));
            categoryRepository.save(new Category("Health"));
            categoryRepository.save(new Category("Music"));
            categoryRepository.save(new Category("Cosmetics"));
            categoryRepository.save(new Category("Other"));
        }
    }
}
