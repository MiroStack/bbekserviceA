package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.UserProfileEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserProfileRepo extends JpaRepository<UserProfileEntity, Long> {
    UserProfileEntity findByUserId(Long id);
    List<UserProfileEntity> findByRoleId(Long roleId);
}
