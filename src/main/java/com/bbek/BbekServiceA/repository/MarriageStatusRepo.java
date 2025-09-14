package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MarriageStatusRfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarriageStatusRepo extends JpaRepository<MarriageStatusRfEntity, Long> {
}
