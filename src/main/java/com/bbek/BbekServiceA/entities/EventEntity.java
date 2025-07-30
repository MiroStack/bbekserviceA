package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="event")
public class EventEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="event_name")
    private String eventName;
    @Column(name="event_type")
    private String eventType;
    @Column(name="event_date")
    private String eventDate;
    @Column(name = "event_time")
    private String event_time;
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
    private String createdDate;
    @Column(name="update_dt")
    private String updateDate;
}
