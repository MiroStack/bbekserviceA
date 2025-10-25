package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MemberDetailsEntity;
import com.bbek.BbekServiceA.entities.MemberEntity;
import com.bbek.BbekServiceA.entities.modified.member.ModifiedMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepo extends JpaRepository<MemberEntity, Long> {
    @Query(
            value = "SELECT *, COUNT(*) OVER() AS total_rows \n" +
                    "            FROM bbek.tbl_member \n" +
                    "            WHERE member_name LIKE :query \n" +
                    "            ORDER BY id DESC  \n" +
                    "            LIMIT 10 " +
                    "            OFFSET :numberOfRowsToSkip ",
            nativeQuery = true
    )
   List<ModifiedMemberEntity> getMembers(@Param("query") String query, @Param("numberOfRowsToSkip") int numberOfRowsToSkip);

    @Query(value = "SELECT\n" +
            " up.id, \n" +
            " up.firstname,\n" +
            " up.middlename,\n" +
            " up.lastname,\n" +
            " up.age,\n" +
            " up.birthdate,\n" +
            " up.address,\n" +
            " up.email,\n" +
            " up.contact_no,\n" +
            " up.emergency_contact_no,\n" +
            " up.emergency_contact_person,\n" +
            " up.relationship_to_contact_person,\n" +
            " up.department_id,\n" +
            " up.position_id,\n" +
            " ba.baptism_officiant,\n" +
            " ba.baptism_officiant_id,\n" +
            " ba.baptism_dt,\n" +
            " tm.is_active,\n" +
            " tm.join_date\n" +
            " FROM user_profile as up\n" +
            " LEFT JOIN baptism as ba  ON up.id = ba.profile_id\n" +
            " LEFT JOIN tbl_member as tm ON up.id = tm.profile_id\n" +
            " WHERE up.id = :memberId;", nativeQuery = true)
    MemberDetailsEntity viewDetails(@Param("memberId") Long memberId);
}
