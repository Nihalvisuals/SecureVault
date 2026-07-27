package com.securevault.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securevault.dto.LoginRequest;
import com.securevault.dto.LoginResponse;
import com.securevault.entity.User;
import com.securevault.repository.UserRepository;
import com.securevault.security.JwtService;
import com.securevault.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public User registerUser(User user) {

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user to database
        return userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {

        Optional<User> optionalUser = userRepository.findByEmail(loginRequest.getEmail());

        if (optionalUser.isEmpty()) {
            return new LoginResponse("Email not found", null);
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return new LoginResponse("Invalid Password", null);
        }

        // Generate JWT Token
        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse("Login Successful", token);
    }
}