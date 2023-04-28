package com.rsww.clientquery.controller;

import com.rsww.clientquery.entity.UserEntity;
import com.rsww.clientquery.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserQueryController {

    private final UserRepository userRepository;

    public UserQueryController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("all")
    List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("{email}")
    List<UserEntity> getUserByEmail(@PathVariable String email) {
        return userRepository.findUserEntityByEmail(email);
    }

}
