package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.UserProfileEntity;

import com.bbek.BbekServiceA.entities.modified.user_profile.ModifiedUserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserProfileRepo extends JpaRepository<UserProfileEntity, Long> {
    UserProfileEntity findByUserId(Long id);
    List<UserProfileEntity> findByRoleId(Long roleId);


    @Query(value =  "SELECT\n" +
            "  tm.id,\n" +
            "  up.firstname,\n" +
            "  up.middlename,\n" +
            "  up.lastname,\n" +
            "  up.age,\n" +
            "  up.birthdate,\n" +
            "  up.address,\n" +
            "  up.email,\n" +
            "  up.gender,\n" +
            "  up.contact_no,\n" +
            "  up.emergency_contact_no,\n" +
            "  up.emergency_contact_person,\n" +
            "  up.relationship_to_contact_person,\n" +
            "  up.department_id,\n" +
            "  up.position_id,\n" +
            "  ba.baptism_officiant,\n" +
            "  ba.baptism_officiant_id,\n" +
            "  ba.baptism_dt,\n" +
            "  tm.is_active,\n" +
            "  tm.join_date,\n" +
            "  COUNT(*) OVER() AS total_rows\n" +
            "FROM bbek.user_profile AS up\n" +
            "LEFT JOIN baptism AS ba ON up.id = ba.profile_id\n" +
            "LEFT JOIN tbl_member AS tm ON up.id = tm.profile_id\n" +
            "WHERE up.role_id = 7\n" +
            "  AND (\n" +
            "    up.firstname LIKE :query\n" +
            "    OR up.middlename LIKE :query\n" +
            "    OR up.lastname LIKE :query\n" +
            "  )\n" +
            "ORDER BY up.id DESC\n" +
            "LIMIT 10 \n" +
            "OFFSET 0", nativeQuery = true)
    List<ModifiedUserProfile> findNotPriestMembers(@Param("query") String query);

    @Query(value = "SELECT\n" +
            "  tm.id,\n" +
            "  up.firstname,\n" +
            "  up.middlename,\n" +
            "  up.lastname,\n" +
            "  up.age,\n" +
            "  up.birthdate,\n" +
            "  up.address,\n" +
            "  up.email,\n" +
            "  up.gender,\n" +
            "  up.contact_no,\n" +
            "  up.emergency_contact_no,\n" +
            "  up.emergency_contact_person,\n" +
            "  up.relationship_to_contact_person,\n" +
            "  up.department_id,\n" +
            "  up.position_id,\n" +
            "  ba.baptism_officiant,\n" +
            "  ba.baptism_officiant_id,\n" +
            "  ba.baptism_dt,\n" +
            "  tm.is_active,\n" +
            "  tm.join_date,\n" +
            "  COUNT(*) OVER() AS total_rows\n" +
            "FROM bbek.user_profile AS up\n" +
            "LEFT JOIN baptism AS ba ON up.id = ba.profile_id\n" +
            "LEFT JOIN tbl_member AS tm ON up.id = tm.profile_id\n" +
            "WHERE up.role_id = 7\n" +
            "  AND (\n" +
            "    up.firstname LIKE :query\n" +
            "    OR up.middlename LIKE :query\n" +
            "    OR up.lastname LIKE :query\n" +
            "  )\n" +
            "ORDER BY up.id DESC\n" +
            "LIMIT 10 \n" +
            "OFFSET :numberOfRowsToSkip;", nativeQuery = true)
    List<ModifiedUserProfile> findPriestMembers(@Param("query") String query, @Param("numberOfRowsToSkip") int numberOfRowsToSkip);
}
