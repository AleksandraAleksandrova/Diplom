package org.elsys.diplom.controller;

import jakarta.validation.Valid;
import org.elsys.diplom.service.UserService;
import org.elsys.diplom.service.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            model.addAttribute("userRegisterDto", userRegisterDto);
            return "register";
        }
        userService.addNewUser(userRegisterDto);
        return "redirect:/login";
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token") String confirmationToken, Model model){
        if (userService.confirmAccount(confirmationToken)) {
            //going to use this message later
            model.addAttribute("message", "Account verified successfully");
        } else {
            //going to use this message later
            model.addAttribute("message", "Verification failed!");
        }
        return "redirect:/login";
    }
}
