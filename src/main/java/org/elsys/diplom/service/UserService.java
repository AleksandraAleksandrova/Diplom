package org.elsys.diplom.service;

import jakarta.validation.Valid;
import org.elsys.diplom.entity.ConfirmationToken;
import org.elsys.diplom.entity.ResetPasswordToken;
import org.elsys.diplom.entity.User;
import org.elsys.diplom.repository.UserRepository;
import org.elsys.diplom.security.CustomUserDetails;
import org.elsys.diplom.service.dto.UserDTO;
import org.elsys.diplom.service.dto.UserRegisterDTO;
import org.elsys.diplom.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Value("${spring.mail.username}")
    private String senderEmail;
    @Value("${spring.config.hostname}")
    private String hostname;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    EmailService emailService;
    @Autowired
    ConfirmationTokenService confirmationTokenService;
    @Autowired
    ResetPasswordTokenService resetPasswordTokenService;

    public void addNewUser(@Valid UserRegisterDTO userRegisterDTO){
        User user = userMapper.toEntity(userRegisterDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(user);

        ConfirmationToken token = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Smart Money Registration!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("To confirm your account, please click here : \n"
                +hostname + "confirm-account?token="+token.getToken() + "\nThis token will expire in 30 minutes.");

        emailService.sendEmail(mailMessage);
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public UserDTO retrieveLoggedInUser(){
        CustomUserDetails loggedInUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(loggedInUser.getId());
        userDTO.setUsername(loggedInUser.getUsername());
        return userDTO;
    }

    public boolean confirmAccount(String confirmationToken){
        if(confirmationTokenService.isTokenNull(confirmationToken)){
            return false;
        }
        ConfirmationToken token = confirmationTokenService.getConfirmationToken(confirmationToken);
        if(token.isExpired()) {
            User user = getUserByEmail(token.getUser().getEmail());
            confirmationTokenService.deleteConfirmationToken(token);
            userRepository.delete(user);
            return false;
        }
        User user = getUserByEmail(token.getUser().getEmail());
        user.setEnabled(true);
        userRepository.save(user);
        return true;
    }

    public void sendResetPasswordEmail(String email){
        User user = getUserByEmail(email);
        ResetPasswordToken token = new ResetPasswordToken(user);
        resetPasswordTokenService.saveResetPasswordToken(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Reset your Smart Money Password!");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("To reset your password, please click here : \n"
                +hostname + "reset-password?token="+token.getToken() + "\nThis token will expire in 1 hour.");

        emailService.sendEmail(mailMessage);
    }

    public boolean resetPassword(String resetPasswordToken, String password){
        if(resetPasswordTokenService.isTokenNull(resetPasswordToken)){
            return false;
        }
        ResetPasswordToken token = resetPasswordTokenService.getResetPasswordToken(resetPasswordToken);
        if(token.isExpired()) {
            return false;
        }
        User user = getUserByEmail(token.getUser().getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

}
