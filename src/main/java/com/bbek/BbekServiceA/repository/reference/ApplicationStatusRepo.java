package com.bbek.BbekServiceA.repository.reference;

import com.bbek.BbekServiceA.entities.reference.ApplicationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationStatusRepo extends JpaRepository<ApplicationStatusEntity, Long> {
    ApplicationStatusEntity findByStatusName(String statusName);
}
