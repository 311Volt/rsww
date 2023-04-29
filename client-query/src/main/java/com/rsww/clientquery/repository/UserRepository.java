package com.rsww.clientquery.repository;

import com.rsww.clientquery.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    UserEntity findUserEntityByEmail(String email);
}
