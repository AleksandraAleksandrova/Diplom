package org.elsys.diplom.controller;

import jakarta.validation.Valid;
import org.elsys.diplom.entity.User;
import org.elsys.diplom.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    UserService userService;

    @GetMapping("/welcome")
    public String getWelcomePage(){
        return "welcome";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute User user, BindingResult result, Model model){
        User existing = userService.getUserByUsername(user.getUsername());
        if(existing != null && existing.getEmail() != null && !existing.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }
        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "/register";
        }
        userService.addNewUser(user);
        return "redirect:/login";
    }
}
