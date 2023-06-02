package com.yetistudios.rsww.rswwgateway.command.dto;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;
}
