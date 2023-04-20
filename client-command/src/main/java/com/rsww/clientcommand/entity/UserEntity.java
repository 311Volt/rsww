package com.rsww.clientcommand.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="user", schema = "public")
public class UserEntity {

    @Id
    private String id;
    private String email;
    private String password;
}
