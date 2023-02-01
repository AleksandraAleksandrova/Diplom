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

    @GetMapping("/forgot-password")
    public String getForgotPasswordPage(){
        return "forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String doForgotPassword(@Valid @RequestParam("email") String email, RedirectAttributes redirectAttributes){
        if(userService.getUserByEmail(email) == null){
            redirectAttributes.addFlashAttribute("errorMessage", "There is no account with this email!\nCheck for typos or register.");
            return "redirect:/forgotPassword";
        }else{
            redirectAttributes.addFlashAttribute("successMessage", "Check your email for reset password link");
        }
        return "redirect:/reset-password";
    }

    /*
    @GetMapping("/reset-password")
    public String getResetPasswordPage(@RequestParam("token") String token, Model model){
        // logic
    }

    @PostMapping("/reset-password")
    public String doResetPassword(@RequestParam("token") String token, @RequestParam("password") String password, RedirectAttributes redirectAttributes){
        // logic
    }
     */
}
