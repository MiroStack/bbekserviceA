package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "tbl_member")
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String fullName;

    @Column(name = "address")
    private String address;

    @Column(name = "age")
    private int age;

    @Column(name="status")
    private String status;

    @Column(name="join_date")
    private String joinDate;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email_address")
    private String email;

    @Column(name = "image")
    private String image;

    @Column(name = "emergency_contact_number")
    private String emergencyContactNumber;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "baptism_date")
    private String baptismDate;

    @Column(name="baptism_officiant")
    private String baptismOfficiant;

    @Column(name="contact_number")
    private String contactNumber;

    @Column(name="birth_date")
    private String birthDate;

}
