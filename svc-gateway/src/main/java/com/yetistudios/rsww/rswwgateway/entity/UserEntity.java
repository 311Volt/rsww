package com.yetistudios.rsww.rswwgateway.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class UserEntity {
    @Id
    private String id;
    private String email;
    private String password;
}
