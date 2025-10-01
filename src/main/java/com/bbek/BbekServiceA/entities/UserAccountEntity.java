package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_account")
@Data
public class UserAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    String username;

    @Column(name= "hashed_password")
    String password;


}
