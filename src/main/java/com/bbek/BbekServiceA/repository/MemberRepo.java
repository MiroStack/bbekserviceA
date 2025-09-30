package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepo extends JpaRepository<MemberEntity, Long> {

}
