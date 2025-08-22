package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.OfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferingRepo extends JpaRepository<OfferingEntity, Long> {
}
