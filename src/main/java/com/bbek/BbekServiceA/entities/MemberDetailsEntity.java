package com.bbek.BbekServiceA.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="user_profile")
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="firstname")
    private String firstname;

    @Column(name="middlename")
    private String middlename;

    @Column(name="lastname")
    private String lastname;

    @Column(name="age")
    private int age;

    @Column(name="birthdate")
    private String birthdate;


    @Column(name="gender")
    private String gender;

    @Column(name="address")
    private String address;

    @Column(name="email")
    private String email;

    @Column(name="contact_no")
    private String contactNo;

    @Column(name="emergency_contact_no")
    private String emergencyContactNo;

    @Column(name="emergency_contact_person")
    private String emergencyContactPerson;

    @Column(name="relationship_to_contact_person")
    private String relationshipToContactPerson;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name="position_id")
    private Long positionId;

    @Column(name="baptism_officiant")
    private String baptismOfficiant;

    @Column(name="baptism_officiant_id")
    private Long baptismOfficiantId;

    @Column(name="baptism_dt")
    private LocalDateTime baptismDate;

    @Column(name="is_active")
    private boolean isActive;

    @Column(name="join_date")
    private LocalDate joinDate;


}
