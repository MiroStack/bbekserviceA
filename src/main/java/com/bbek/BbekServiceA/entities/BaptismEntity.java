package com.bbek.BbekServiceA.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "baptism")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaptismEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="profile_id")
    private long profileId;

    @Column(name = "testimony")
    private String testimony;

    @Column(name = "preferred_dt")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate preferred_dt;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "baptism_date")
    private String baptismDate;

    @Column(name = "scheduled_dt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime scheduledDate;

    @Column(name="baptism_officiant")
    private String baptismOfficiant;

    @Column(name = "baptism_officiant_id")
    private long baptismOfficiantId;

    @Column(name = "created_dt")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;





}
