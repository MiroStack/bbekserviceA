package com.bbek.BbekServiceA.entities.reference;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "member_application_statuses")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "status_name")
    private String statusName;

}
