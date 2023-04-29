package com.rsww.clientquery.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="user", schema = "public")
public class UserEntity {

    @Id
    private String id;
    private String email;
    private String password;
}
