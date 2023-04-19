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

    /**
     * This method is used to map the userRegisterDTO to an entity and save it in the database.
     * It creates and sends a confirmation email to the user.
     * @param userRegisterDTO - the user to be registered
     * @see UserRegisterDTO
     */
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

    /**
     * This method is used to get a user by his email.
     * @param email - the email we are searching for
     * @return the user with the given email
     * if there is no user with the given email, it returns null
     */
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    /**
     * This method is used to get a user by his username.
     * @param username - the email we are searching for
     * @return the user with the given email
     * if there is no user with the given email, it returns null
     */
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    /**
     * This method is used to get the logged-in user.
     * @return the logged-in user as a UserDTO
     * @see UserDTO
     */
    public UserDTO retrieveLoggedInUser(){
        CustomUserDetails loggedInUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = new UserDTO();
        userDTO.setId(loggedInUser.getId());
        userDTO.setUsername(loggedInUser.getUsername());
        return userDTO;
    }

    /**
     * This method is used to confirm the user's account.
     * @param confirmationToken - the token that is used to confirm the user's account
     * @return true if the account is confirmed, false if the token is invalid or expired
     */
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

    /**
     * This method is used to send a reset password email to the user.
     * @param email - the email of the user who wants to reset his password
     */
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

    /**
     * This method is used to reset the user's password.
     * @param resetPasswordToken - the token that is used to reset the user's password
     * @param password - the new password
     * @return true if the password is reset, false if the token is invalid or expired
     */
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
