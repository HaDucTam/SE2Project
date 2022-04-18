package com.example.se2project.service.impl;
import com.example.se2project.entity.User;
import com.example.se2project.repository.UserRepository;
import com.example.se2project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.Optional;


@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository> implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isExistedEmail(String email) {
        return userRepository.existsByEmail(email.toLowerCase());
    }

    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).get();
        return user;
    }

//    @Override
//    public Optional<User> findUserByEmail(String email) throws NotFoundException {
//        User user = userRepository.findUserByEmail(email).get();
//        if (user == null) {
//            throw new NotFoundException("User " + email + "not found !");
//        }
//
//        return user;
//    }

}
