package com.bbek.BbekServiceA.repository.reference;

import com.bbek.BbekServiceA.entities.reference.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
}
