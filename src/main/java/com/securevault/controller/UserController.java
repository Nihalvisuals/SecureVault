package com.securevault.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securevault.dto.ChangePasswordRequest;
import com.securevault.dto.LoginRequest;
import com.securevault.dto.LoginResponse;
import com.securevault.dto.UpdateUserRequest;
import com.securevault.dto.UserResponse;
import com.securevault.entity.User;
import com.securevault.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@Valid @RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {

        String email = authentication.getName();

        return userService.getCurrentUser(email);
    }

    @PutMapping("/profile")
    public UserResponse updateProfile(
            Authentication authentication,
            @RequestBody UpdateUserRequest request) {

        String email = authentication.getName();

        return userService.updateProfile(email, request);
    }

    @PutMapping("/change-password")
    public String changePassword(
            Authentication authentication,
            @RequestBody ChangePasswordRequest request) {

        String email = authentication.getName();

        return userService.changePassword(email, request);
    }

    @DeleteMapping("/delete")
    public String deleteAccount(Authentication authentication) {

        String email = authentication.getName();

        return userService.deleteAccount(email);
    }
}