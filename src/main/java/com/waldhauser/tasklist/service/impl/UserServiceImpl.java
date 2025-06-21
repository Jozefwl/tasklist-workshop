package com.waldhauser.tasklist.service.impl;

import com.waldhauser.tasklist.config.support.JwtMaker;
import com.waldhauser.tasklist.domain.model.User;
import com.waldhauser.tasklist.domain.repository.UserRepository;
import com.waldhauser.tasklist.rest.model.user.LoginResponse;
import com.waldhauser.tasklist.service.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtMaker jwtMaker;

    public UserServiceImpl(UserRepository userRepository, JwtMaker jwtMaker) {
        this.userRepository = userRepository;
        this.jwtMaker = jwtMaker;
    }

    // ------------------ REGISTER -------------------
    public User register(String name, String password) {
        // Check if user already exists
        if (userRepository.findByName(name).isPresent()) {
            throw new IllegalStateException("Username already taken");
        }

        // save new user to database
        return userRepository.save(new User(name, password));
    }
    // ----------------------------------------------

    // ------------------- LOGIN --------------------
    public LoginResponse login(String name, String password) {
        // find user
        if (userRepository.findByName(name).isPresent()) {
            // finds user and his password
            User user = userRepository.findByName(name).get();
            if (user.getPassword().equals(password)) {
                return new LoginResponse(user.getId(), user.getName(),jwtMaker.generateToken(user));
            }
        } else { throw new IllegalStateException("Wrong username or password."); }

        // default return, likely won't ever get here
        throw new IllegalStateException("Wrong username or password.");
    }
    // ----------------------------------------------
}