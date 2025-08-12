package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepo extends JpaRepository<EventEntity, Long> {
    @Query("SELECT e.filePath FROM EventEntity e WHERE LOWER(e.eventName) = LOWER(:eventName)")
    String findFilePathByName(@Param("eventName") String eventName);

    @Query(value = "SELECT * FROM event WHERE event_start_date > now() ORDER BY event_start_date limit 4", nativeQuery = true)
    List<EventEntity> findUpcomingEvent();
}
