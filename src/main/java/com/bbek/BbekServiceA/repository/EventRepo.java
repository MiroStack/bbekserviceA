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

    @Query(value = "SELECT \n" +
            "  e.id,\n" +
            "  e.event_name,\n" +
            "  e.event_type,\n" +
            "  e.event_location,\n" +
            "  e.attendance,\n" +
            "  e.offering,\n" +
            "  e.status_id,\n" +
            "  e.filepath,\n" +
            "  e.created_dt,\n" +
            "  e.update_dt,\n" +
            "  e.description,\n" +
            "  e.event_start_date,\n" +
            "  e.event_end_date,\n" +
            "  res.status_name,\n" +
            "  COUNT(*) OVER() AS total_rows\n" +
            "FROM bbek.event AS e\n" +
            "INNER JOIN rf_event_status res ON e.status_id = res.id\n" +
            "WHERE \n" +
            "  ('asd' IS NULL OR 'asd' = '' OR (\n" +
            "      e.event_name LIKE :query\n" +
            "      OR e.event_type LIKE :query\n" +
            "      OR e.event_location LIKE :query\n" +
            "      OR e.attendance LIKE :query\n" +
            "      OR e.offering LIKE :query\n" +
            "      OR e.description LIKE :query\n" +
            "      OR res.status_name LIKE :query\n" +
            "  ))\n" +
            "AND\n" +
            "  (:status IS NULL OR :status = '' OR res.status_name LIKE CONCAT('%', :status, '%'))\n" +
            "ORDER BY e.id DESC\n" +
            "LIMIT 10\n" +
            "OFFSET 0;", nativeQuery = true)
    List<ModifiedEventEntity> paginatedEvents(@Param("query") String query, @Param("numberOfRowsToSkip") int numberOfRowsToSkip, @Param("status") String status);

}
