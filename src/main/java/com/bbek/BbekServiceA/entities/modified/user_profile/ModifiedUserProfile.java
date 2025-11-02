package com.bbek.BbekServiceA.entities.modified.user_profile;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_profile")
@Data
public class ModifiedUserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String middlename;
    private String lastname;
    private Integer age;
    private String birthdate;
    private String address;
    private String email;
    private String gender;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "emergency_contact_no")
    private String emergencyContactNo;

    @Column(name = "emergency_contact_person")
    private String emergencyContactPerson;

    @Column(name = "relationship_to_contact_person")
    private String relationshipToContactPerson;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "position_id")
    private Long positionId;

    @Column(name = "baptism_officiant")
    private String baptismOfficiant;

    @Column(name = "baptism_officiant_id")
    private Long baptismOfficiantId;

    @Column(name = "baptism_dt")
    private String baptismDt;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "join_date")
    private String joinDate;

    @Column(name = "total_rows")
    private Integer totalRows;
}

