package com.rsww.client.command.controller;

import com.rsww.client.command.dto.CreateUserRequest;
import com.rsww.client.command.service.UserCommandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserCommandService userCommandService;

    public UserController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    @CrossOrigin
    @PostMapping(value = "/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserRequest request) {
        try {
            CompletableFuture<String> response = userCommandService.createUser(request);
            return new ResponseEntity<>(response.get(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

