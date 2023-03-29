package com.wrapped.service;

import com.wrapped.entity.User;
import com.wrapped.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    public UserRepository userRepository;


    public List<User> listallUsers() {
        return userRepository.findAll();
    }

    public User loginUser(User user) {
        return userRepository.findUserByEmailAndPassword(user.getEmail(), user.getPassword());
    }
}
