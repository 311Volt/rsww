package com.rsww.client.command.service;

import com.rsww.client.command.command.CreateUserCommand;
import com.rsww.client.command.dto.CreateUserRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class UserCommandService {

    private final CommandGateway commandGateway;

    public UserCommandService(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    public CompletableFuture<String> createUser(CreateUserRequest createUserRequest){
        return commandGateway.send(new CreateUserCommand(
                UUID.randomUUID().toString(),
                createUserRequest.getEmail(),
                createUserRequest.getPassword()
        ));
    }
}
