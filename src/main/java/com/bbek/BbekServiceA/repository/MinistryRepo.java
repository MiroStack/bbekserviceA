package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MinistryEntity;
import com.bbek.BbekServiceA.entities.modified.minsitry.ModifiedMinistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MinistryRepo extends JpaRepository<MinistryEntity, Long> {
    @Query("SELECT m.filepath FROM MinistryEntity m WHERE LOWER(m.ministryName) = LOWER(:ministryName)")
    String findFilePathByName(@Param("ministryName") String ministryName);

    @Query(value = "SELECT * FROM ministry ORDER BY created_dt LIMIT 4", nativeQuery = true )
    List<MinistryEntity> findUpcomingMinistry();

    @Query(value = "SELECT *, COUNT(*) OVER() as total_rows FROM " +
            "bbek.ministry " +
            "WHERE ministry_name LIKE :query " +
            "OR schedules LIKE :query " +
            "OR leader LIKE :query " +
            "OR descriptions LIKE :query " +
            "OR members LIKE :query " +
            "ORDER BY id DESC "+
            "LIMIT 10 "+
            "OFFSET :noOfRowsToSkip", nativeQuery = true)
    List<ModifiedMinistryEntity> getPaginatedMinistry(@Param("query")String query, @Param("noOfRowsToSkip")int noOfRowsToSkip);
}
