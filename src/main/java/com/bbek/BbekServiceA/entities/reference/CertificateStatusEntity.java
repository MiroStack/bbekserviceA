package com.bbek.BbekServiceA.entities.reference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="rf_certificate_status")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateStatusEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="status_name")
    private String statusName;
}
