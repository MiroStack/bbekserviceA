package com.bbek.BbekServiceA.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    Long id;

    @Column(name="firstname")
    String firstname;

    @Column(name="lastname")
    String lastname;

    @Column(name="email")
    String email;

    @Column(name = "phone_number")
    String phoneNumber;

    @Column(name = "testimony")
    String testimony;

    @Column(name = "created_dt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;

    @Column(name = "preferred_dt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime preferred_dt;

    @Column(name = "status_id")
    private Long statusId;

}
