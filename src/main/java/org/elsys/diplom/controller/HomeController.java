package org.elsys.diplom.controller;

import jakarta.validation.Valid;
import org.elsys.diplom.service.CategoryService;
import org.elsys.diplom.service.ExpenseService;
import org.elsys.diplom.service.UserService;
import org.elsys.diplom.service.dto.ExpenseDTO;
import org.elsys.diplom.service.dto.FilterExpensesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    ExpenseService expenseService;

    @GetMapping("/home")
    public String getHomePage(Model model){
        model.addAttribute("usersExpenses", expenseService.getUsersExpenses(userService.retrieveLoggedInUser().getId()));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("user", userService.retrieveLoggedInUser());
        model.addAttribute("newExpense", new ExpenseDTO());
        return "home";
    }

    @PostMapping("/home")
    public String postHomePage(@ModelAttribute("newExpense") @Valid ExpenseDTO newExpense, BindingResult result, Model model){
        if(result.hasErrors()){
            /*
            System.out.println("Dates:");
            System.out.println(newExpense.getStartDate());
            System.out.println(newExpense.getEndDate());
            System.out.println("Errors: ");
            for(ObjectError error : result.getAllErrors()){
                System.out.println(error.getDefaultMessage());
            }
             */
            model.addAttribute("newExpense", newExpense);
            model.addAttribute("usersExpenses", expenseService.getUsersExpenses(userService.retrieveLoggedInUser().getId()));
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("user", userService.retrieveLoggedInUser());
            return "home";

        }
        expenseService.addExpense(newExpense);
        return "redirect:/home";
    }

    @GetMapping("/statistics")
    public String getStatisticsPage(Model model){
        model.addAttribute("weekExp", expenseService.getLastWeekExpenses(userService.retrieveLoggedInUser().getId()));
        model.addAttribute("monthExp", expenseService.getLastMonthExpenses(userService.retrieveLoggedInUser().getId()));
        return "stats";
    }

    @PostMapping("/filterStatistics")
    public String customFiltering(@ModelAttribute("filter") @Valid FilterExpensesDTO filter, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            model.addAttribute("filter", filter);
            model.addAttribute("user", userService.retrieveLoggedInUser());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "customStatistic";
        }
        redirectAttributes.addFlashAttribute("customExp", expenseService.customFilterExpenses(filter));
        return "redirect:/filterStatistics";
    }

    @GetMapping("/filterStatistics")
    public String getCustomFilteringPage(Model model) {
        model.addAttribute("user", userService.retrieveLoggedInUser());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("filter", new FilterExpensesDTO());
        return "customStatistic";
    }
}
