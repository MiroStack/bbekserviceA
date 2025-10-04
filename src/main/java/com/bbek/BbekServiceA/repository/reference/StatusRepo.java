package com.bbek.BbekServiceA.repository.reference;

import com.bbek.BbekServiceA.entities.reference.StatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepo extends JpaRepository<StatusEntity, Long> {
    StatusEntity findByStatusName(String statusName);
}
