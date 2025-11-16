package com.bbek.BbekServiceA.entities.modified.event;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EventMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "status_name")
    private String statusName;


    @Column(name="created_dt")
    private LocalDateTime createdDt;
}
