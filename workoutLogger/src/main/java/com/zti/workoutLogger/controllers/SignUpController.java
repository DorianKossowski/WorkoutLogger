package com.zti.workoutLogger.controllers;

import com.zti.workoutLogger.models.dto.UserDto;
import com.zti.workoutLogger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class SignUpController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public UserDto signUpUser(@RequestBody UserDto user) {
        userService.createUser(user);
        return user;
    }
}