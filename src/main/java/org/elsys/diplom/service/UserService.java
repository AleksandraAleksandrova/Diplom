package org.elsys.diplom.service;

import org.elsys.diplom.dto.UserLoginDTO;
import org.elsys.diplom.entity.User;
import org.elsys.diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void addNewUser(User user){
        userRepository.save(user);
    }

    public boolean isExistingUser(UserLoginDTO user){
        return userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword()).isPresent();
    }
}
