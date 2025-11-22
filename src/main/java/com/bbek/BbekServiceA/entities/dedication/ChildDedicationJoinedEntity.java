package com.bbek.BbekServiceA.entities.dedication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChildDedicationJoinedEntity {
    @Column(name ="id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_name")
    private String childName;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(name = "guardian_name")
    private String guardianName;

    @Column(name = "status_name")
    private String status;

    @Column(name = "dedication_date")
    private LocalDateTime dedicationDt;

    @Column(name = "contact_number")
    private String contactNo;

    @Column(name = "modified_dt")
    private LocalDateTime modifiedDt;

    @Column(name = "created_dt")
    private LocalDateTime createdDt;


}
