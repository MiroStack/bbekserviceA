package com.bbek.BbekServiceA.repository.reference;

import com.bbek.BbekServiceA.entities.reference.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepo extends JpaRepository<DepartmentEntity, Long> {

    DepartmentEntity findByDepartmentName(String department);
}
