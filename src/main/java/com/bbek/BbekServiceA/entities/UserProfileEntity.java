package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_profile")
@Data
public class UserProfileEntity {
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
    @Column(name = "address")
    private String address;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "email")
    private String email;
    @Column(name = "created_dt")
    private String createdDate;
    @Column(name = "contact_no")
    private String contactNo;
    @Column(name = "emergency_contact_person")
    private String emergencyContactPerson;
    @Column(name = "emergency_contact_no")
    private String emergencyContactNo;
    @Column(name = "gender")
    private String gender;
    @Column(name = "profile_image")
    private String profileImage;
    @Column(name = "role_id")
    private Long roleId;
    @Column(name = "image_uuid")
    private String imageUUID;

    @Column(name = "relationship_to_contact_person")
    private String relationshipToContactPerson;

    @Column(name = "department_id")
    private String departmentId;

    @Column(name = "position_id")
    private String positionId;
}
