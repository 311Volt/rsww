package com.yetistudios.rsww.rswwgateway.query.controller;

import com.yetistudios.rsww.rswwgateway.query.entity.UserEntity;
import com.yetistudios.rsww.rswwgateway.query.query.FindUserByEmailQuery;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class QueryUserController {

    @Autowired
    private QueryGateway queryGateway;


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
