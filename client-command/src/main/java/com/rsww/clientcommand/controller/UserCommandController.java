package com.rsww.clientcommand.controller;

import com.rsww.clientcommand.dto.UserInput;
import com.rsww.clientcommand.entity.UserEntity;
import com.rsww.clientcommand.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserCommandController {

    private final UserService userService;

    public UserCommandController(UserService userService) {
        this.userService = userService;
    }

    @CrossOrigin
    @PostMapping("/new")
    public UserEntity createUser(@RequestBody UserInput userInput){
        UserEntity userCreated = new UserEntity(UUID.randomUUID().toString(), userInput.getEmail(), userInput.getPassword());
        this.userService.createUser(userCreated);
        return userCreated;
    }
}
