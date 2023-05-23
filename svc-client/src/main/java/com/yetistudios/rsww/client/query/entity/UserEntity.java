package com.yetistudios.rsww.client.query.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UserEntity {
    @Id
    private String id;
    private String email;
    private String password;
}
