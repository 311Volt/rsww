package com.yetistudios.rsww.rswwgateway.command.service;

import com.yetistudios.rsww.rswwgateway.command.command.CreateUserCommand;
import com.yetistudios.rsww.rswwgateway.command.dto.CreateUserRequest;
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
