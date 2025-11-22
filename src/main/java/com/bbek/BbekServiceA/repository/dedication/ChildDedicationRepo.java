package com.bbek.BbekServiceA.repository.dedication;

import com.bbek.BbekServiceA.entities.dedication.ChildDedicationEntity;
import com.bbek.BbekServiceA.entities.dedication.ChildDedicationJoinedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChildDedicationRepo extends JpaRepository<ChildDedicationEntity, Long> {

    @Query(value = """
            SELECT
                cd.id,
                cd.child_name,
                cd.birthdate,
                cd.guardian_name,
                ss.status_name,
                cd.dedication_date,
                cd.contact_number,
                cd.modified_dt,
                cd.created_dt
            FROM child_dedication cd
            LEFT JOIN service_statuses ss
                ON cd.status = ss.id
            WHERE cd.guardian_name LIKE CONCAT('%', :query, '%')
              OR cd.child_name LIKE CONCAT('%', :query, '%')
              OR(:statusName IS NULL OR :statusName = '' OR ss.status_name LIKE CONCAT('%', :statusName, '%'))
            LIMIT 10
            OFFSET :numOfRowsToSkip
            """,
            nativeQuery = true)
    List<ChildDedicationJoinedEntity> fetchChildDedicationDetails(@Param("query") String query, @Param("numOfRowsToSkip") int numOfRowsToSkip, @Param("statusName") String statusName);

    @Query(value = """
            SELECT COUNT(*) FROM  child_dedication cd
            """, nativeQuery = true)
    Long getTotalDedications();

    @Query(value = """
            SELECT COUNT(*) FROM  child_dedication cd
            LEFT JOIN service_statuses ss
            ON cd.status = ss.id
            WHERE ss.id = 4
            """, nativeQuery = true)
    Long getTotalCompletedDedication();

    @Query(value = """
            SELECT COUNT(*) FROM  child_dedication cd
            LEFT JOIN service_statuses ss
            ON cd.status = ss.id
            WHERE ss.id = 1
            """, nativeQuery = true)
    Long getTotalNewDedication();

    @Query(value = """
            SELECT
                cd.id,
                cd.child_name,
                cd.birthdate,
                cd.guardian_name,
                ss.status_name,
                cd.dedication_date,
                cd.contact_number,
                cd.modified_dt,
                cd.created_dt
            FROM child_dedication cd
            LEFT JOIN service_statuses ss
                ON cd.status = ss.id
            WHERE cd.guardian_name LIKE CONCAT('%', :query, '%')
              AND cd.child_name LIKE CONCAT('%', :query, '%')
              AND(:statusName IS NULL OR :statusName = '' OR ss.status_name LIKE CONCAT('%', :statusName, '%'))
              AND cd.created_by_id = :memberId
            LIMIT 10
            OFFSET :numOfRowsToSkip
            """,
            nativeQuery = true)
    List<ChildDedicationJoinedEntity> fetchAllMemberRequestDedication(@Param("query") String query, @Param("numOfRowsToSkip") int numOfRowsToSkip, @Param("statusName") String statusName, @Param("memberId")Long memberId);


}
