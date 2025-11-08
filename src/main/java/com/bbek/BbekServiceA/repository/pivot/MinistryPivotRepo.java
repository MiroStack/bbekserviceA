package com.bbek.BbekServiceA.repository.pivot;

import com.bbek.BbekServiceA.entities.pivot.MinistryPivotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinistryPivotRepo extends JpaRepository<MinistryPivotEntity, Long> {

}
