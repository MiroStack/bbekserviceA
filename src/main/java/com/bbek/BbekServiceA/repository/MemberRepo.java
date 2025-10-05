package com.bbek.BbekServiceA.repository;

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
}
