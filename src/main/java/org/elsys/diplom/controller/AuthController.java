package org.elsys.diplom.controller;

import jakarta.validation.Valid;
import org.elsys.diplom.entity.User;
import org.elsys.diplom.service.UserService;
import org.elsys.diplom.service.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        model.addAttribute("userRegisterDto", new UserRegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String doRegister(@ModelAttribute("userRegisterDto") @Valid UserRegisterDTO userRegisterDto, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println("\nErrors: ");
            for(ObjectError error : result.getAllErrors()){
                System.out.println(error.getDefaultMessage());
                System.out.println(error.getObjectName());
                System.out.println(error.getCode());
            }
            model.addAttribute("userRegisterDto", userRegisterDto);
            return "register";
        }
        userService.addNewUser(userRegisterDto);
        return "redirect:/login";
    }
}
