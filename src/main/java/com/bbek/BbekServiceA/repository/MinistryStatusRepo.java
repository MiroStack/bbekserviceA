package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MinistryStatusRfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MinistryStatusRepo extends JpaRepository<MinistryStatusRfEntity, Long> {
    MinistryStatusRfEntity findByStatusName(String statusName);
}
