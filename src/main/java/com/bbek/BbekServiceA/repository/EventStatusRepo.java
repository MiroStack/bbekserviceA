package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.EventStatusRfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventStatusRepo extends JpaRepository<EventStatusRfEntity, Long> {
    EventStatusRfEntity findByStatusName(String statusName);
}
