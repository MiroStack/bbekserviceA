package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="rf_event_status")
public class EventStatusRfEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "status_name")
    private String statusName;
}
