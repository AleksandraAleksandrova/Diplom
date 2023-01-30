package org.elsys.diplom.controller;

import jakarta.validation.Valid;
import org.elsys.diplom.service.UserService;
import org.elsys.diplom.service.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        model.addAttribute("userMail", userRegisterDto.getEmail());
        return "verify";
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String confirmUserAccount(@RequestParam("token") String confirmationToken, RedirectAttributes redirectAttributes){
        if (userService.confirmAccount(confirmationToken)) {
            redirectAttributes.addFlashAttribute("successMessage", "Account verified successfully");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Verification failed!");
        }
        return "redirect:/login";
    }
}
