package com.bbek.BbekServiceA.repository.reference;

import com.bbek.BbekServiceA.entities.reference.PositionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepo extends JpaRepository<PositionEntity, Long> {

}
