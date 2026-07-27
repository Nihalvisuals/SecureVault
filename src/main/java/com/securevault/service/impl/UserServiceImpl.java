package com.securevault.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.securevault.dto.ChangePasswordRequest;
import com.securevault.dto.LoginRequest;
import com.securevault.dto.LoginResponse;
import com.securevault.dto.UpdateUserRequest;
import com.securevault.dto.UserResponse;
import com.securevault.entity.User;
import com.securevault.exception.InvalidPasswordException;
import com.securevault.exception.UserNotFoundException;
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

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("Email not found"));

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword())) {

            throw new InvalidPasswordException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse("Login Successful", token);
    }

    @Override
    public UserResponse getCurrentUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        return new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail()
        );
    }

    @Override
    public UserResponse updateProfile(String email, UpdateUserRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        user.setFullName(request.getFullName());

        User updatedUser = userRepository.save(user);

        return new UserResponse(
                updatedUser.getId(),
                updatedUser.getFullName(),
                updatedUser.getEmail()
        );
    }

    @Override
    public String changePassword(String email, ChangePasswordRequest request) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new InvalidPasswordException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);

        return "Password changed successfully";
    }

    @Override
    public String deleteAccount(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found"));

        userRepository.delete(user);

        return "Account deleted successfully";
    }
}