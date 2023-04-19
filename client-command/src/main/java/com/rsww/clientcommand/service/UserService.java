package com.rsww.clientcommand.service;

import com.rsww.clientcommand.entity.UserEntity;
import com.rsww.clientcommand.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserEntity user) {
        this.userRepository.save(user);
    }
}
