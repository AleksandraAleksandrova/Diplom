package org.elsys.diplom.service;

import org.elsys.diplom.entity.User;
import org.elsys.diplom.repository.UserRepository;
import org.elsys.diplom.security.CustomUserDetails;
import org.elsys.diplom.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;

    public void addNewUser(User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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
}
