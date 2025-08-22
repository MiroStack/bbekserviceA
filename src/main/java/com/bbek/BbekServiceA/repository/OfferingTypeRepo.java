package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.OfferingEntity;
import com.bbek.BbekServiceA.entities.OfferingTypeRfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferingTypeRepo extends JpaRepository<OfferingTypeRfEntity, Long> {
   OfferingTypeRfEntity findByOfferingTypeName(String typeName);
}
