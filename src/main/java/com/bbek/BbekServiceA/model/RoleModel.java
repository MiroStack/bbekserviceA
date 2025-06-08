package com.bbek.BbekServiceA.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "rf_role")
@Data
public class RoleModel {
    @Id
    private Long id;

    @Column(name = "rolename")
    private String roleName;
}
