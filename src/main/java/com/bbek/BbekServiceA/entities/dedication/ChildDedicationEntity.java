package com.bbek.BbekServiceA.entities.dedication;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "child_dedication")
public class ChildDedicationEntity {
    @Column(name ="id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_name")
    private String childName;

    @Column(name = "birthdate")
    private String birthDate;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "status")
    private Long status;

    @Column(name = "dedication_date")
    private LocalDateTime dedicationDt;

    @Column(name = "contact_number")
    private String contactNo;

    @Column(name = "modified_dt")
    private LocalDateTime modifiedDt;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "modified_by_id")
    private Long modifiedById;


}
