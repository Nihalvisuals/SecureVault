package com.securevault.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securevault.entity.User;
import com.securevault.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        System.out.println("================================");
        System.out.println("Name     : " + user.getFullName());
        System.out.println("Email    : " + user.getEmail());
        System.out.println("Password : " + user.getPassword());
        System.out.println("================================");

        User savedUser = userService.registerUser(user);

        System.out.println("Saved User ID : " + savedUser.getId());

        return savedUser;
    }
}