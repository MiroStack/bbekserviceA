package com.bbek.BbekServiceA.repository.reference;

import com.bbek.BbekServiceA.entities.reference.ServiceStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceStatusRepo extends JpaRepository<ServiceStatusEntity, Long> {
}
