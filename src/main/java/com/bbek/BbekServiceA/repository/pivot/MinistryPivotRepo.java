package com.bbek.BbekServiceA.repository.pivot;

import com.bbek.BbekServiceA.entities.pivot.EventPivotEntity;
import com.bbek.BbekServiceA.entities.pivot.MinistryPivotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MinistryPivotRepo extends JpaRepository<MinistryPivotEntity, Long> {
    MinistryPivotEntity findByMinistryId(Long ministryId);
    List<MinistryPivotEntity> findByMemberId(Long memberId);
}
