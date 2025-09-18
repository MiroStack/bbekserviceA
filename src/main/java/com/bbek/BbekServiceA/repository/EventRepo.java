package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.EventEntity;
import com.bbek.BbekServiceA.entities.modified.event.ModifiedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepo extends JpaRepository<EventEntity, Long> {
    @Query("SELECT e.filePath FROM EventEntity e WHERE LOWER(e.eventName) = LOWER(:eventName)")
    String findFilePathByName(@Param("eventName") String eventName);

    @Query(value = "SELECT * FROM event WHERE event_start_date > now() ORDER BY event_start_date limit 4", nativeQuery = true)
    List<EventEntity> findUpcomingEvent();

    @Query(value = "SELECT *, COUNT(*) OVER() AS total_rows " +
            "FROM bbek.event " +
            "WHERE event_name LIKE :query " +
            "ORDER BY id " +
            "LIMIT 10 "+
            "OFFSET :numberOfRowsToSkip", nativeQuery = true)
    List<ModifiedEventEntity> paginatedEvents(@Param("query") String query, @Param("numberOfRowsToSkip") int numberOfRowsToSkip);

}
