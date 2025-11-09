package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.ministries.MinistryEntity;
import com.bbek.BbekServiceA.entities.ministries.MinistryMemberEntity;
import com.bbek.BbekServiceA.entities.modified.minsitry.ModifiedMinistryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MinistryRepo extends JpaRepository<MinistryEntity, Long> {
    @Query("SELECT m.filepath FROM MinistryEntity m WHERE LOWER(m.ministryName) = LOWER(:ministryName)")
    String findFilePathByName(@Param("ministryName") String ministryName);

    @Query(value = "SELECT * FROM ministry ORDER BY created_dt LIMIT 4", nativeQuery = true)
    List<MinistryEntity> findUpcomingMinistry();

    @Query(value = """
                SELECT m.*, rd.department_name, COUNT(*) OVER() AS total_rows
                FROM bbek.ministry AS m
                LEFT JOIN bbek.rf_department AS rd ON m.department_id = rd.id
                WHERE 
                    m.ministry_name LIKE CONCAT('%', :query, '%')
                    OR m.schedules LIKE CONCAT('%', :query, '%')
                    OR m.leader LIKE CONCAT('%', :query, '%')
                    OR m.descriptions LIKE CONCAT('%', :query, '%')
                    OR rd.department_name LIKE CONCAT('%', :query, '%')
                    OR m.members LIKE CONCAT('%', :query, '%')
                ORDER BY m.id DESC
                LIMIT 10 OFFSET :noOfRowsToSkip
            """, nativeQuery = true)
    List<ModifiedMinistryEntity> getPaginatedMinistry(
            @Param("query") String query,
            @Param("noOfRowsToSkip") int noOfRowsToSkip
    );

    @Query(value = """
                SELECT m.*, rd.department_name, COUNT(*) OVER() AS total_rows
                FROM bbek.ministry AS m
                LEFT JOIN bbek.rf_department AS rd ON m.department_id = rd.id
                WHERE
                    m.department_id = 1
                    AND(
                    m.ministry_name LIKE CONCAT('%', :query, '%')
                    OR m.schedules LIKE CONCAT('%', :query, '%')
                    OR m.leader LIKE CONCAT('%', :query, '%')
                    OR m.descriptions LIKE CONCAT('%', :query, '%')
                    OR rd.department_name LIKE CONCAT('%', :query, '%')
                    OR m.members LIKE CONCAT('%', :query, '%'))
                ORDER BY m.id DESC
                LIMIT 10 OFFSET :noOfRowsToSkip
            """, nativeQuery = true)
    List<ModifiedMinistryEntity> getAllWomenMinistry(
            @Param("query") String query,
            @Param("noOfRowsToSkip") int noOfRowsToSkip
    );

    @Query(value = """
                SELECT m.*, rd.department_name, COUNT(*) OVER() AS total_rows
                FROM bbek.ministry AS m
                LEFT JOIN bbek.rf_department AS rd ON m.department_id = rd.id
                WHERE
                    m.department_id = 2
                    AND(
                    m.ministry_name LIKE CONCAT('%', :query, '%')
                    OR m.schedules LIKE CONCAT('%', :query, '%')
                    OR m.leader LIKE CONCAT('%', :query, '%')
                    OR m.descriptions LIKE CONCAT('%', :query, '%')
                    OR rd.department_name LIKE CONCAT('%', :query, '%')
                    OR m.members LIKE CONCAT('%', :query, '%'))
                ORDER BY m.id DESC
                LIMIT 10 OFFSET :noOfRowsToSkip
            """, nativeQuery = true)
    List<ModifiedMinistryEntity> getAllMenMinistry(
            @Param("query") String query,
            @Param("noOfRowsToSkip") int noOfRowsToSkip
    );

    @Query(value = """
                SELECT m.*, rd.department_name, COUNT(*) OVER() AS total_rows
                FROM bbek.ministry AS m
                LEFT JOIN bbek.rf_department AS rd ON m.department_id = rd.id
                WHERE
                    m.department_id = 3
                    AND(
                    m.ministry_name LIKE CONCAT('%', :query, '%')
                    OR m.schedules LIKE CONCAT('%', :query, '%')
                    OR m.leader LIKE CONCAT('%', :query, '%')
                    OR m.descriptions LIKE CONCAT('%', :query, '%')
                    OR rd.department_name LIKE CONCAT('%', :query, '%')
                    OR m.members LIKE CONCAT('%', :query, '%'))
                ORDER BY m.id DESC
                LIMIT 10 OFFSET :noOfRowsToSkip
            """, nativeQuery = true)
    List<ModifiedMinistryEntity> getYoungPeopleMinistry(
            @Param("query") String query,
            @Param("noOfRowsToSkip") int noOfRowsToSkip
    );

    @Query(value = """
                SELECT m.*, rd.department_name, COUNT(*) OVER() AS total_rows
                FROM bbek.ministry AS m
                LEFT JOIN bbek.rf_department AS rd ON m.department_id = rd.id
                LEFT JOIN bbek.ministry_pivot_table as mpt ON m.id = mpt.ministry_id
                WHERE
                    mpt.member_id = :userId
                    AND(
                    m.ministry_name LIKE CONCAT('%', :query, '%')
                    OR m.schedules LIKE CONCAT('%', :query, '%')
                    OR m.leader LIKE CONCAT('%', :query, '%')
                    OR m.descriptions LIKE CONCAT('%', :query, '%')
                    OR rd.department_name LIKE CONCAT('%', :query, '%')
                    OR m.members LIKE CONCAT('%', :query, '%'))
                ORDER BY m.id DESC
                LIMIT 10 OFFSET :noOfRowsToSkip
            """, nativeQuery = true)
    List<ModifiedMinistryEntity> getMyMinistries(
            @Param("query") String query,
            @Param("noOfRowsToSkip") int noOfRowsToSkip,
            @Param("userId") Long id
    );

    @Query(value = """
            SELECT COUNT(*) FROM ministry
            """, nativeQuery = true)
    Long totalMinistries();

    @Query(value = """
            SELECT COUNT(*) FROM ministry WHERE status_id = 1
            """, nativeQuery = true)
    Long totalActiveMinistries();

    @Query(value = """
            SELECT COUNT(*) FROM pivot_ministry_table WHERE status_id = 2
            """, nativeQuery = true)
    Long totalMinistryMembers();


    @Query(value = """
            SELECT\s
            mpt.id,
            CONCAT(up.firstname,' ',up.middlename,' ',up.middlename) as fullname,
            m.ministry_name,
            mas.status_name,
            mpt.created_dt
            FROM user_profile up
            LEFT JOIN tbl_member tm ON up.id = tm.profile_id
            RIGHT JOIN ministry_pivot_table mpt ON tm.id = mpt.member_id
            LEFT JOIN ministry m ON mpt.ministry_id = m.id
            LEFT JOIN member_application_statuses mas ON mpt.status_id = mas.id
            WHERE m.id = 21
            AND(
               CONCAT(up.firstname, ' ', up.middlename, ' ', up.lastname) LIKE CONCAT('%', :query, '%')
               or mas.status_name  LIKE CONCAT('%', :query, '%')
            )
            LIMIT 20
            OFFSET 0
            """, nativeQuery = true)
    List<MinistryMemberEntity> findAllMinistryMembers(@Param("ministryId") Long ministryId, @Param("query") String query, @Param("numberOfRowsToSkip") int numberOfRowsToSkip);


    @Query(value = """
            SELECT COUNT(*) FROM pivot_ministry_table
            WHERE status_id = 2
            AND ministry_id = :ministryId
            """, nativeQuery = true)
  Long totalMembersPerMinistry(@Param("ministryId") Long ministryId);

}
