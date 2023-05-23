package com.yetistudios.rsww.client.command.controller;

import com.yetistudios.rsww.client.command.dto.CreateUserRequest;
import com.yetistudios.rsww.client.command.service.UserCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserCommandService userCommandService;


    @CrossOrigin
    @PostMapping(value = "/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        try {
            this.userCommandService.createUser(request);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

