package com.rsww.client.query.controller;

import com.rsww.client.query.entity.UserEntity;
import com.rsww.client.query.query.FindUserByEmailQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class QueryUserController {

    private final QueryGateway queryGateway;

    public QueryUserController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @CrossOrigin
    @GetMapping("/get-user/{email}")
    public ResponseEntity<UserEntity> getUser(@PathVariable String email){
        UserEntity userEntity = queryGateway.query(
                new FindUserByEmailQuery(email), UserEntity.class
        ).join();

        if (userEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userEntity, HttpStatus.OK);
    }
}
