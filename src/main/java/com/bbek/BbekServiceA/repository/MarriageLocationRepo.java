package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MarriageLocationRfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarriageLocationRepo extends JpaRepository<MarriageLocationRfEntity, Long> {
}
