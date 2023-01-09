package org.elsys.diplom.service;

import org.elsys.diplom.service.dto.UserLoginDTO;
import org.elsys.diplom.entity.User;
import org.elsys.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean isExistingUserDTO(UserLoginDTO user){
        return userRepository.findByUsername(user.getUsername()) != null;
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
