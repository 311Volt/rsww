package com.yetistudios.rsww.client.query.service;

import com.yetistudios.rsww.client.common.event.UserCreatedEvent;
import com.yetistudios.rsww.client.query.entity.UserEntity;
import com.yetistudios.rsww.client.query.query.FindUserByEmailQuery;
import com.yetistudios.rsww.client.query.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueryUserService {

    @Autowired
    private UserRepository repository;

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
        log.info("Handling FindUserByEmailQuery...");
        return repository.findByEmail(query.getEmail()).orElse(null);
    }
}
