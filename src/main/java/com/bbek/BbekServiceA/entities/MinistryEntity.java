package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ministry")
public class MinistryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="schedules")
    private String schedule;

    @Column(name = "leader")
    private String leader;

    @Column(name = "status_id")
    private int statusId;

    @Column(name = "ministry_name")
    private String ministryName;

    @Column(name ="descriptions")
    private String description;

    @Column(name ="members")
    private int member;

    @Column(name = "filepath")
    private String filepath;

    @Column(name = "created_dt")
    private String createdDate;

    @Column(name = "updated_dt")
    private String updatedDate;
}
