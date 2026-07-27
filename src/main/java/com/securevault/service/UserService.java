package com.securevault.service;

import com.securevault.dto.LoginRequest;
import com.securevault.dto.LoginResponse;
import com.securevault.dto.UserResponse;
import com.securevault.entity.User;

public interface UserService {

    // Register User
    User registerUser(User user);

    // Login User
    LoginResponse loginUser(LoginRequest loginRequest);

    // Get Current Logged-in User
    UserResponse getCurrentUser(String email);

}