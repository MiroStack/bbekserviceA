package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MinistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MinistryRepo extends JpaRepository<MinistryEntity, Long> {
    @Query("SELECT m.filepath FROM MinistryEntity m WHERE LOWER(m.ministryName) = LOWER(:ministryName)")
    String findFilePathByName(@Param("ministryName") String ministryName);
}
