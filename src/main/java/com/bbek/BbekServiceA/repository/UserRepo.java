package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
}
