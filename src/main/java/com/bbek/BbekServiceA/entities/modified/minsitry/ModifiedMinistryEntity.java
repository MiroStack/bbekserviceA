package com.bbek.BbekServiceA.entities.modified.minsitry;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Data
@Table(name = "ministry")
public class ModifiedMinistryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="schedules")
    private String schedule;

    @Column(name = "leader")
    private String leader;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "ministry_name")
    private String ministryName;

    @Column(name ="descriptions")
    private String description;

    @Column(name ="members")
    private int member;

    @Column(name = "filepath")
    private String filepath;

    @Column(name = "created_dt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;


    @Column(name = "updated_dt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedDate;

    @Column(name="start_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @Column(name="end_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @Column(name = "total_rows")
    private int totalRows;


}
