package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_profile")
@Data
public class UserProfileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "middlename")
    private String middlename;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "age")
    private int age;
    @Column(name = "birthdate")
    private String birthdate;
    @Column(name = "user_id")
    private Long userId;
    @Column(name="address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "created_dt")
    private String createdDate;
}
