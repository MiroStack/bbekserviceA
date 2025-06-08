package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.UserProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepo extends JpaRepository<UserProfileModel, Long> {
    UserProfileModel findByUserId(Long id);
}
