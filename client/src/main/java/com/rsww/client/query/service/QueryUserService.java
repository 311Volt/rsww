package com.rsww.client.query.service;

import com.rsww.client.common.event.UserCreatedEvent;
import com.rsww.client.query.entity.UserEntity;
import com.rsww.client.query.query.FindUserByEmailQuery;
import com.rsww.client.query.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueryUserService {

    private final UserRepository repository;

    public QueryUserService(UserRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(UserCreatedEvent userCreatedEvent){
        log.info("Handling UserCreatedEvent...");
        UserEntity userEntity = new UserEntity();

        userEntity.setId(userCreatedEvent.getId());
        userEntity.setEmail(userCreatedEvent.getEmail());
        userEntity.setPassword(userCreatedEvent.getPassword());

        repository.save(userEntity);
    }

    @QueryHandler
    public UserEntity handle(FindUserByEmailQuery query) {
        log.info("Handling FindUserByIdQuery...");
        return repository.findByEmail(query.getEmail()).orElse(null);
    }
}
