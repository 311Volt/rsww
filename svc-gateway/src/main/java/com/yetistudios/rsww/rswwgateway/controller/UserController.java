package com.yetistudios.rsww.rswwgateway.controller;

import com.yetistudios.rsww.rswwgateway.entity.UserEntity;
import com.yetistudios.rsww.rswwgateway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserRepository userRepository;


    @CrossOrigin
    @PostMapping(value = "/create")
    public ResponseEntity<String> createUser(@RequestBody UserEntity user) {
        try {
            UserEntity userEntity = userRepository.findByEmail(user.getEmail()).orElse(null);
            if (userEntity == null) {
                user.setId(UUID.randomUUID().toString());
                this.userRepository.save(user);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CrossOrigin
    @GetMapping("/get-user/{email}")
    public ResponseEntity<UserEntity> getUser(@PathVariable String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
        if (userEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}

