package com.bbek.BbekServiceA.entities.modified.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="event")
public class ModifiedEventEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="event_name")
    private String eventName;
    @Column(name="event_type")
    private String eventType;
    @Column(name= "event_location")
    private String eventLocation;
    @Column(name = "attendance")
    private int attendance;
    @Column(name ="offering")
    private int offering;
    @Column(name = "status_id")
    private long statusId;
    @Column(name="description")
    private String description;
    @Column(name="filepath")
    private String filePath;
    @Column(name = "created_dt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;
    @Column(name="update_dt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updateDate;
    @Column(name="event_end_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventEndDate;
    @Column(name="event_start_date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime eventStartDate;
    @Column(name = "total_rows")
    private int totalRows;
}
