package org.elsys.diplom.controller;

import org.elsys.diplom.entity.User;
import org.elsys.diplom.security.CustomUserDetails;
import org.elsys.diplom.service.CategoryService;
import org.elsys.diplom.entity.Category;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;

    /*
    @GetMapping("/addCategory")
    public String getAddCategoryPage(){
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String postAddCategoryPage(@ModelAttribute Category category){
        categoryService.addCategory(category);
        return "redirect:/welcome";
    }
     */

    @GetMapping("/home")
    public String getHomePage(Model model){
        model.addAttribute("categories", categoryService.getAllCategories());
        CustomUserDetails loggedInUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userName", loggedInUser.getUsername());
        model.addAttribute("userId", loggedInUser.getId());
        model.addAttribute("newExpense", new ExpenseDTO());
        return "home";
    }

}
