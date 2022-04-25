package com.example.se2project.service.impl;

import com.example.se2project.entity.User;
import com.example.se2project.repository.UserRepository;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isExistedEmail(String email) {
        return userRepository.existsByEmail(email.toLowerCase());
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

}
