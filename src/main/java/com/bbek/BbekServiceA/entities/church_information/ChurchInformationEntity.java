package com.bbek.BbekServiceA.entities.church_information;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "church_information")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChurchInformationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "church_name")
    private String churchName;

    @Column(name = "acronym")
    private String acronym;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "website")
    private String website;

    @Column(name = "mision")
    private String mission;

    @Column(name ="address")
    private String address;

    @Column(name = "vision")
    private String vision;

    @Column(name = "modified_by_id")
    private Long modifiedById;

    @Column(name="modified_dt")
    private LocalDateTime modifiedDt;

    @Column(name = "logo_image_path")
    private String logoImagePath;
}
