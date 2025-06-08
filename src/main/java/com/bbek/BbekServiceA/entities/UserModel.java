package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_account")
@Data
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    String username;

    @Column(name= "password")
    String password;

    @Column(name = "role_id")
    int role_id;

    @Column(name = "status_id")
    int status_id;
}
