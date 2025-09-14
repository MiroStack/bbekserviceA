package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="marriage")
@AllArgsConstructor
@NoArgsConstructor
public class MarriageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "groom_name")
    private String groomName;

    @Column(name = "bride_name")
    private String brideName;

    @Column(name = "wedding_date")
    LocalDateTime weddingDate;

    @Column(name = "status")
    Long status;

    @Column(name = "location")
    Long location;

    @Column(name = "contact_email")
    String email;
}
