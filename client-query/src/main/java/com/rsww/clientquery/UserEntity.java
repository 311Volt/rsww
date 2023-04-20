package com.rsww.clientquery;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="user", schema = "public")
public class UserEntity {

    @Id
    private String id;
    private String email;
    private String password;
}
