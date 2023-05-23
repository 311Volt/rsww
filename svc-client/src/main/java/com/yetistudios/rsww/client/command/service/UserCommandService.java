package com.yetistudios.rsww.client.command.service;

import com.yetistudios.rsww.client.command.command.CreateUserCommand;
import com.yetistudios.rsww.client.command.dto.CreateUserRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UserCommandService {

    @Autowired
    private CommandGateway commandGateway;


    public CompletableFuture<String> createUser(CreateUserRequest createUserRequest){
        return commandGateway.send(new CreateUserCommand(
                UUID.randomUUID().toString(),
                createUserRequest.getEmail(),
                createUserRequest.getPassword()
        ));
    }
}
