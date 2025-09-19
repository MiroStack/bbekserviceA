package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MarriageEntity;
import com.bbek.BbekServiceA.entities.modified.marriage.ModifiedMarriageEntity;
import com.bbek.BbekServiceA.entities.modified.minsitry.ModifiedMinistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarriageRepo extends JpaRepository<MarriageEntity, Long> {
    @Query(value = "SELECT *, COUNT(*) OVER() as total_rows FROM " +
            "bbek.marriage " +
            "WHERE groom_name LIKE :query " +
            "OR bride_name LIKE :query " +
            "OR contact_email LIKE :query " +
            "ORDER BY id DESC "+
            "LIMIT 10 "+
            "OFFSET :noOfRowsToSkip", nativeQuery = true)
    List<ModifiedMarriageEntity> getPaginatedMarriage(@Param("query")String query, @Param("noOfRowsToSkip")int noOfRowsToSkip);
}
