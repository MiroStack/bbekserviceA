package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MinistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MinistryRepo extends JpaRepository<MinistryEntity, Long> {
    @Query("SELECT m.filepath FROM MinistryEntity m WHERE LOWER(m.ministryName) = LOWER(:ministryName)")
    String findFilePathByName(@Param("ministryName") String ministryName);

    @Query(value = "SELECT * FROM ministry ORDER BY created_dt LIMIT 4", nativeQuery = true )
    List<MinistryEntity> findUpcomingMinistry();
}
