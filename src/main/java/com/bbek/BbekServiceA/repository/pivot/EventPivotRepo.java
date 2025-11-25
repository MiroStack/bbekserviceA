package com.bbek.BbekServiceA.repository.pivot;

import com.bbek.BbekServiceA.entities.pivot.EventPivotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventPivotRepo extends JpaRepository<EventPivotEntity, Long> {

    EventPivotEntity findByEventId(Long eventId);
    List<EventPivotEntity> findByMemberId(Long memberId);

}
