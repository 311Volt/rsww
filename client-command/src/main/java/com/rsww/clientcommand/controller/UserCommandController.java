package com.rsww.clientcommand.controller;

import com.rsww.clientcommand.dto.UserInput;
import com.rsww.clientcommand.entity.UserEntity;
import com.rsww.clientcommand.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserCommandController {

    private final UserService userService;

    public UserCommandController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/new")
    public UserEntity createUser(@RequestBody UserInput userInput){
        UserEntity userCreated = new UserEntity(UUID.randomUUID().toString(), userInput.getEmail(), userInput.getPassword());
        this.userService.createUser(userCreated);
        return userCreated;
    }
}
