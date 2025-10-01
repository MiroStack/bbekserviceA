package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.UserAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserAccountEntity, Long> {
    UserAccountEntity findByUsername(String username);
}
