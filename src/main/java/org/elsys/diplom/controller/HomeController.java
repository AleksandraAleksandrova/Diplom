package org.elsys.diplom.controller;

import org.elsys.diplom.service.CategoryService;
import org.elsys.diplom.service.ExpenseService;
import org.elsys.diplom.service.UserService;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.elsys.diplom.service.dto.filterExpensesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    ExpenseService expenseService;

    /*
    @GetMapping("/addCategory")
    public String getAddCategoryPage(){
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String postAddCategoryPage(@ModelAttribute Category category){
        categoryService.addCategory(category);
        return "redirect:/addCategory";
    }
     */

    @GetMapping("/home")
    public String getHomePage(Model model){
        model.addAttribute("usersExpenses", expenseService.getUsersExpenses(userService.retrieveLoggedInUser().getId()));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("user", userService.retrieveLoggedInUser());
        model.addAttribute("newExpense", new ExpenseDTO());
        return "home";
    }

    @PostMapping("/home")
    public String postHomePage(@ModelAttribute ExpenseDTO expenseDTO){
        expenseService.addExpense(expenseDTO);
        return "redirect:/home";
    }

    @GetMapping("/statistics")
    public String getStatisticsPage(Model model){
        model.addAttribute("weekExp", expenseService.getLastWeekExpenses(userService.retrieveLoggedInUser().getId()));
        model.addAttribute("monthExp", expenseService.getLastMonthExpenses(userService.retrieveLoggedInUser().getId()));
        return "stats";
    }

    @GetMapping("/filterStatistics")
    public String getCustomFilteringPage(Model model){
        model.addAttribute("user", userService.retrieveLoggedInUser());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("filter", new filterExpensesDTO());
        return "customStatistic";
    }


    @PostMapping("/filterStatistics")
    public String customFiltering(@ModelAttribute filterExpensesDTO filter, Model model){
        model.addAttribute("customExp", expenseService.customFilterExpenses(filter));
        return "redirect:/filterStatistics";
    }
}
