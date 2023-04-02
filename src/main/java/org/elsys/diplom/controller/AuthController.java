package org.elsys.diplom.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.elsys.diplom.service.ResetPasswordTokenService;
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
    @Autowired
    ResetPasswordTokenService resetPasswordTokenService;

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
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Verification failed! Your token expired or you had a typo.");
            return "redirect:/register";
        }

    }

    @GetMapping("/forgot-password")
    public String getForgotPasswordPage(){
        return "forgotPassword";
    }

    @RequestMapping(value="forgot-password", method = RequestMethod.POST)
    public String forgotUserPassword(@Valid @Email @RequestParam("email") String email, Model model){
        if(userService.getUserByEmail(email) == null){
            model.addAttribute("errorMessage", "There is no account with this email!\nCheck for typos or register.");
            return "forgotPassword";
        }
        userService.sendResetPasswordEmail(email);
        model.addAttribute("message", "Check your email for reset password link.");
        return "messageView";
    }

    @GetMapping("/reset-password")
    public String getResetPage(@RequestParam("token") String resetPasswordToken, Model model){
        model.addAttribute("token", resetPasswordToken);
        return "resetPassword";
    }

    @PostMapping("/reset-password")
    public String resetUserPassword(@RequestParam("token") String resetPasswordToken,
                                    @RequestParam("password") String password,
                                    RedirectAttributes redirectAttributes){
        boolean isPasswordResetSuccessful = userService.resetPassword(resetPasswordToken, password);
        if (!isPasswordResetSuccessful) {
            if(password.isBlank() || password.isEmpty()){
                redirectAttributes.addFlashAttribute("errorMessage", "Password cannot be empty.");
                return "redirect:/reset-password?token=" + resetPasswordToken;
            }
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid token.");
            return "redirect:/reset-password";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Password reset successfully");
        return "redirect:/login";
    }
}
