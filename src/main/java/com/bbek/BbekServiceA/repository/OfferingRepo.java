package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.OfferingEntity;
import com.bbek.BbekServiceA.entities.modified.offering.ModifiedOfferingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OfferingRepo extends JpaRepository<OfferingEntity, Long> {
    @Query(value = "SELECT *, COUNT(*) OVER() as total_rows FROM " +
            "bbek.offering " +
            "WHERE member_name LIKE :query " +
            "OR amount LIKE :query " +
            "OR notes LIKE :query " +
            "ORDER BY id DESC "+
            "LIMIT 10 "+
            "OFFSET :noOfRowsToSkip ", nativeQuery = true)
    List<ModifiedOfferingEntity> getPaginatedOffering(@Param("query") String query, @Param("noOfRowsToSkip") int noOfRowsToSkip);
}
