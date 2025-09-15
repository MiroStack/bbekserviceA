package com.bbek.BbekServiceA.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime weddingDate;

    @Column(name = "status")
    Long status;

    @Column(name = "location")
    Long location;

    @Column(name = "contact_email")
    String email;

    @Column(name = "created_dt")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime createdDate;
}
