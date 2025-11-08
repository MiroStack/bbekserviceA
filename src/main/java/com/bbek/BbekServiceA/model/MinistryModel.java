package com.bbek.BbekServiceA.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class MinistryModel {
    private long id;

    private String schedule;

    private String leader;

    private String statusName;

    private String ministryName;

    private String description;
    private String department;

    private int member;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;


    private LocalTime startTime;


    private LocalTime endTime;

    private int totalRows;



    public MinistryModel(long id, String schedule, String leader, String statusName, String ministryName, String description, String department, int member, LocalDateTime createdDate, LocalDateTime updatedDate, LocalTime startTime, LocalTime endTime, int totalRows) {
        this.id = id;
        this.schedule = schedule;
        this.leader = leader;
        this.statusName = statusName;
        this.ministryName = ministryName;
        this.description = description;
        this.department = department;
        this.member = member;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalRows =totalRows;
    }
}
