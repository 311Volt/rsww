package com.yetistudios.rsww.rswwgateway.query.query;

import lombok.Data;

@Data
public class FindUserByEmailQuery {

    private String email;

    public FindUserByEmailQuery(String email) {
        this.email = email;
    }
}
