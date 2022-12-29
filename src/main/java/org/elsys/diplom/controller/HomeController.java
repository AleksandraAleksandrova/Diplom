package org.elsys.diplom.controller;

import org.elsys.diplom.entity.Category;
import org.elsys.diplom.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @Autowired
    CategoryRepository categoryRepository;

    @GetMapping("/welcome")
    public String getWelcomePage(){
        return "welcome";
    }
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }

    @GetMapping("/addCategory")
    public String getAddCategoryPage(){
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String postAddCategoryPage(@ModelAttribute Category category){
        categoryRepository.save(category);
        return "redirect:/welcome";
    }
}
