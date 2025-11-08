package com.bbek.BbekServiceA.repository.pivot;

import com.bbek.BbekServiceA.entities.pivot.EventPivotEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventPivotRepo extends JpaRepository<EventPivotEntity, Long> {

    EventPivotEntity findByEventId(Long eventId);

}
