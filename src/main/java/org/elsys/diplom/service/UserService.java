package org.elsys.diplom.service;

import jakarta.validation.Valid;
import org.elsys.diplom.entity.ConfirmationToken;
import org.elsys.diplom.entity.User;
import org.elsys.diplom.repository.UserRepository;
import org.elsys.diplom.security.CustomUserDetails;
import org.elsys.diplom.service.dto.UserDTO;
import org.elsys.diplom.service.dto.UserRegisterDTO;
import org.elsys.diplom.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    EmailService emailService;
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    public void addNewUser(@Valid UserRegisterDTO userRegisterDTO){
        User user = userMapper.toEntity(userRegisterDTO);
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        userRepository.save(user);

        ConfirmationToken token = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(token);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Smart Money Registration!");
        mailMessage.setFrom("a.aleksandrova2004@gmail.com");
        mailMessage.setText("To confirm your account, please click here : \n"
                +"http://localhost:8080/confirm-account?token="+token.getToken());

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
        if(!confirmationTokenService.isTokenNull(confirmationToken)){
            ConfirmationToken token = confirmationTokenService.getConfirmationToken(confirmationToken);
            User user = getUserByEmail(token.getUser().getEmail());
            user.setEnabled(true);
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }

}
